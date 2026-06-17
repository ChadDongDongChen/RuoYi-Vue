package com.ruoyi.web.photo.util;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson2.JSONObject;

public class WxMiniProgramUtil
{
    private static final Logger log = LoggerFactory.getLogger(WxMiniProgramUtil.class);

    private static final RestTemplate restTemplate = new RestTemplate();

    private static final String CODE2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";

    public static Map<String, String> code2Session(String appId, String appSecret, String code)
    {
        String url = CODE2SESSION_URL
            + "?appid=" + appId
            + "&secret=" + appSecret
            + "&js_code=" + code
            + "&grant_type=authorization_code";

        try
        {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            JSONObject json = JSONObject.parseObject(response.getBody());

            String openid = json.getString("openid");
            String sessionKey = json.getString("session_key");
            String unionId = json.getString("unionid");
            String errcode = json.getString("errcode");
            String errmsg = json.getString("errmsg");

            if (openid == null)
            {
                log.error("wx code2session failed: errcode={}, errmsg={}", errcode, errmsg);
                return null;
            }

            Map<String, String> result = new HashMap<>();
            result.put("openid", openid);
            result.put("session_key", sessionKey);
            if (unionId != null)
            {
                result.put("unionid", unionId);
            }
            return result;
        }
        catch (Exception e)
        {
            log.error("wx code2session exception", e);
            return null;
        }
    }
}
