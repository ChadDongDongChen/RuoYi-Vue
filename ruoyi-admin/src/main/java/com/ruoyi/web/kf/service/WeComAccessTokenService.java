package com.ruoyi.web.kf.service;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.web.kf.config.WecomKfProperties;

@Service
public class WeComAccessTokenService
{
    private static final Logger log = LoggerFactory.getLogger(WeComAccessTokenService.class);
    private static final String CACHE_KEY = "wecom:kf:access_token";

    @Autowired
    private WecomKfProperties wecomKfProperties;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    @Qualifier("kfRestTemplate")
    private RestTemplate restTemplate;

    public String getAccessToken()
    {
        if (!wecomKfProperties.isConfigured())
        {
            return null;
        }
        String cached = redisCache.getCacheObject(CACHE_KEY);
        if (cached != null && !cached.isEmpty())
        {
            return cached;
        }
        String url = String.format("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s",
                wecomKfProperties.getCorpId(), wecomKfProperties.getSecret());
        try
        {
            String body = restTemplate.getForObject(url, String.class);
            JSONObject o = JSON.parseObject(body);
            if (o == null || o.getIntValue("errcode") != 0)
            {
                log.warn("gettoken failed: {}", body);
                return null;
            }
            String token = o.getString("access_token");
            int expires = o.getIntValue("expires_in");
            if (token != null)
            {
                int seconds = Math.max(60, expires - 120);
                redisCache.setCacheObject(CACHE_KEY, token, seconds, TimeUnit.SECONDS);
            }
            return token;
        }
        catch (Exception e)
        {
            log.error("gettoken exception", e);
            return null;
        }
    }
}
