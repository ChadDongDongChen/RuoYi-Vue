package com.ruoyi.web.kf.service;

import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.web.kf.domain.KfMessage;
import com.ruoyi.web.kf.domain.KfSyncCursor;
import com.ruoyi.web.kf.mapper.KfCustomerMapper;
import com.ruoyi.web.kf.mapper.KfMessageMapper;
import com.ruoyi.web.kf.mapper.KfSyncCursorMapper;

@Service
public class KfWecomInboundOrchestrator
{
    private static final Logger log = LoggerFactory.getLogger(KfWecomInboundOrchestrator.class);

    @Autowired
    private WeComKfApiClient weComKfApiClient;

    @Autowired
    private KfSyncCursorMapper kfSyncCursorMapper;

    @Autowired
    private KfMessageMapper kfMessageMapper;

    @Autowired
    private KfCustomerMapper kfCustomerMapper;

    @Autowired
    private KfAiReplyRunner kfAiReplyRunner;

    public void handleKfMsgOrEvent(String callbackToken, String openKfid)
    {
        if (StringUtils.isBlank(openKfid))
        {
            return;
        }
        KfSyncCursor cur = kfSyncCursorMapper.selectByOpenKfid(openKfid);
        String cursor = cur != null ? cur.getNextCursor() : "";
        boolean firstPull = true;
        for (int guard = 0; guard < 50; guard++)
        {
            JSONObject res = weComKfApiClient.syncMsg(openKfid, cursor, firstPull ? callbackToken : null);
            firstPull = false;
            if (res == null || res.getIntValue("errcode") != 0)
            {
                log.warn("sync_msg stop: {}", res != null ? res.toJSONString() : "null");
                break;
            }
            String nextCursor = res.getString("next_cursor");
            if (nextCursor != null)
            {
                cursor = nextCursor;
                kfSyncCursorMapper.upsertCursor(openKfid, cursor);
            }
            JSONArray list = res.getJSONArray("msg_list");
            if (list != null)
            {
                for (int i = 0; i < list.size(); i++)
                {
                    processOneMessage(list.getJSONObject(i));
                }
            }
            if (res.getIntValue("has_more") != 1)
            {
                break;
            }
        }
    }

    private void processOneMessage(JSONObject o)
    {
        if (o == null)
        {
            return;
        }
        int origin = o.getIntValue("origin");
        String msgtype = o.getString("msgtype");
        String msgid = o.getString("msgid");
        String openKfid = o.getString("open_kfid");
        String externalUserid = o.getString("external_userid");
        Long sendTime = o.getLong("send_time");
        if (StringUtils.isBlank(msgid))
        {
            return;
        }
        if (origin == 3 && "text".equals(msgtype))
        {
            JSONObject text = o.getJSONObject("text");
            String content = text != null ? text.getString("content") : null;
            if (StringUtils.isBlank(content))
            {
                return;
            }
            if (StringUtils.isBlank(externalUserid) || StringUtils.isBlank(openKfid))
            {
                return;
            }
            kfCustomerMapper.upsertActive(externalUserid, openKfid, "");
            if (kfMessageMapper.selectKfMessageByMsgid(msgid) != null)
            {
                return;
            }
            KfMessage inbound = new KfMessage();
            inbound.setMsgid(msgid);
            inbound.setExternalUserid(externalUserid);
            inbound.setOpenKfid(openKfid);
            inbound.setDirection("1");
            inbound.setMsgtype("text");
            inbound.setContent(content);
            inbound.setSendTime(sendTime);
            inbound.setCreateTime(new Date());
            try
            {
                kfMessageMapper.insertKfMessage(inbound);
            }
            catch (DuplicateKeyException e)
            {
                return;
            }
            kfAiReplyRunner.replyAsync(msgid, externalUserid, openKfid, content);
        }
    }
}
