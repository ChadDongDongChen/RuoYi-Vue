package com.ruoyi.web.kf.service;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

@Service
public class WeComKfApiClient
{
    private static final Logger log = LoggerFactory.getLogger(WeComKfApiClient.class);

    @Autowired
    @Qualifier("kfRestTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private WeComAccessTokenService accessTokenService;

    /**
     * 拉取消息（含回调 token 时优先带上，降低限频）
     */
    public JSONObject syncMsg(String openKfid, String cursor, String callbackToken)
    {
        String token = accessTokenService.getAccessToken();
        if (token == null)
        {
            return null;
        }
        String url = "https://qyapi.weixin.qq.com/cgi-bin/kf/sync_msg?access_token=" + token;
        Map<String, Object> body = new HashMap<>();
        body.put("open_kfid", openKfid);
        body.put("limit", 1000);
        body.put("voice_format", 0);
        if (cursor != null && !cursor.isEmpty())
        {
            body.put("cursor", cursor);
        }
        if (callbackToken != null && !callbackToken.isEmpty())
        {
            body.put("token", callbackToken);
        }
        return postJson(url, body);
    }

    public boolean sendText(String externalUserid, String openKfid, String content, String clientMsgId)
    {
        String token = accessTokenService.getAccessToken();
        if (token == null)
        {
            return false;
        }
        String url = "https://qyapi.weixin.qq.com/cgi-bin/kf/send_msg?access_token=" + token;
        Map<String, Object> text = new HashMap<>();
        text.put("content", content);
        Map<String, Object> body = new HashMap<>();
        body.put("touser", externalUserid);
        body.put("open_kfid", openKfid);
        body.put("msgtype", "text");
        body.put("text", text);
        if (clientMsgId != null && !clientMsgId.isEmpty())
        {
            body.put("msgid", clientMsgId);
        }
        JSONObject res = postJson(url, body);
        if (res == null)
        {
            return false;
        }
        if (res.getIntValue("errcode") != 0)
        {
            log.warn("send_msg failed: {}", res.toJSONString());
            return false;
        }
        return true;
    }

    private JSONObject postJson(String url, Map<String, Object> body)
    {
        try
        {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(JSON.toJSONString(body), headers);
            String resp = restTemplate.postForObject(url, entity, String.class);
            return JSON.parseObject(resp);
        }
        catch (Exception e)
        {
            log.error("wecom api post {}", url, e);
            return null;
        }
    }

    public static JSONArray msgList(JSONObject syncResponse)
    {
        if (syncResponse == null)
        {
            return null;
        }
        return syncResponse.getJSONArray("msg_list");
    }
}
