package com.ruoyi.web.kf.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.ruoyi.web.kf.config.AiKfProperties;

@Service
public class AiKfChatService
{
    private static final Logger log = LoggerFactory.getLogger(AiKfChatService.class);

    @Autowired
    private AiKfProperties aiKfProperties;

    @Autowired
    @Qualifier("kfRestTemplate")
    private RestTemplate restTemplate;

    public String complete(String userMessage, String productCatalogBlock)
    {
        if (!aiKfProperties.isEnabled() || !aiKfProperties.isConfigured())
        {
            return aiKfProperties.getFallbackReply();
        }
        String url = trimEnd(aiKfProperties.getBaseUrl(), '/') + "/chat/completions";
        try
        {
            List<Map<String, String>> messages = new ArrayList<>();
            Map<String, String> sys = new HashMap<>();
            sys.put("role", "system");
            sys.put("content", aiKfProperties.getSystemPrompt() + "\n\n可推荐商品：\n" + productCatalogBlock);
            messages.add(sys);
            Map<String, String> user = new HashMap<>();
            user.put("role", "user");
            user.put("content", userMessage);
            messages.add(user);
            Map<String, Object> body = new HashMap<>();
            body.put("model", aiKfProperties.getModel());
            body.put("messages", messages);
            body.put("temperature", 0.7);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(aiKfProperties.getApiKey());
            HttpEntity<String> entity = new HttpEntity<>(JSON.toJSONString(body), headers);
            String resp = restTemplate.postForObject(url, entity, String.class);
            JSONObject o = JSON.parseObject(resp);
            if (o == null)
            {
                return aiKfProperties.getFallbackReply();
            }
            JSONArray choices = o.getJSONArray("choices");
            if (choices == null || choices.isEmpty())
            {
                return aiKfProperties.getFallbackReply();
            }
            JSONObject msg = choices.getJSONObject(0).getJSONObject("message");
            String content = msg.getString("content");
            return content != null && !content.isEmpty() ? content.trim() : aiKfProperties.getFallbackReply();
        }
        catch (Exception e)
        {
            log.error("ai chat completions failed", e);
            return aiKfProperties.getFallbackReply();
        }
    }

    private static String trimEnd(String s, char ch)
    {
        if (s == null)
        {
            return "";
        }
        int end = s.length();
        while (end > 0 && s.charAt(end - 1) == ch)
        {
            end--;
        }
        return s.substring(0, end);
    }
}
