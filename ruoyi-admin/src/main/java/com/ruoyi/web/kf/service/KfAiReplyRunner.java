package com.ruoyi.web.kf.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.ruoyi.web.kf.domain.KfMessage;
import com.ruoyi.web.kf.domain.KfProduct;
import com.ruoyi.web.kf.mapper.KfMessageMapper;

@Service
public class KfAiReplyRunner
{
    private static final Logger log = LoggerFactory.getLogger(KfAiReplyRunner.class);

    @Autowired
    private KfProductRecommendService kfProductRecommendService;

    @Autowired
    private AiKfChatService aiKfChatService;

    @Autowired
    private WeComKfApiClient weComKfApiClient;

    @Autowired
    private KfMessageMapper kfMessageMapper;

    @Async("kfTaskExecutor")
    public void replyAsync(String customerMsgId, String externalUserid, String openKfid, String userText)
    {
        try
        {
            List<KfProduct> rec = kfProductRecommendService.recommendForQuestion(userText);
            String catalog = kfProductRecommendService.formatCatalogForPrompt(rec);
            String reply = aiKfChatService.complete(userText, catalog);
            reply = truncateUtf8(reply, 2000);
            String clientMsgId = buildClientMsgId();
            boolean ok = weComKfApiClient.sendText(externalUserid, openKfid, reply, clientMsgId);
            if (ok)
            {
                KfMessage out = new KfMessage();
                out.setMsgid(clientMsgId);
                out.setExternalUserid(externalUserid);
                out.setOpenKfid(openKfid);
                out.setDirection("2");
                out.setMsgtype("text");
                out.setContent(reply);
                out.setSendTime(System.currentTimeMillis() / 1000);
                out.setCreateTime(new Date());
                kfMessageMapper.insertKfMessage(out);
            }
        }
        catch (Exception e)
        {
            log.error("kf ai reply failed", e);
        }
    }

    private static String buildClientMsgId()
    {
        String u = UUID.randomUUID().toString().replace("-", "");
        return ("a" + u).substring(0, 32);
    }

    private static String truncateUtf8(String s, int maxBytes)
    {
        if (s == null || s.isEmpty())
        {
            return s;
        }
        byte[] b = s.getBytes(StandardCharsets.UTF_8);
        if (b.length <= maxBytes)
        {
            return s;
        }
        int end = maxBytes;
        while (end > 0 && (b[end] & 0xC0) == 0x80)
        {
            end--;
        }
        return new String(b, 0, end, StandardCharsets.UTF_8);
    }
}
