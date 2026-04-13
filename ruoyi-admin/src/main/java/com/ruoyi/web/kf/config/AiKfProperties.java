package com.ruoyi.web.kf.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ai.kf")
public class AiKfProperties
{
    private boolean enabled = true;
    private String baseUrl = "https://api.openai.com/v1";
    private String apiKey = "";
    private String model = "gpt-4o-mini";
    private String systemPrompt = "你是热情专业的微信客服，用简短口语回复。若涉及价格以商品库为准，不要编造不存在的优惠。";
    private String fallbackReply = "您好，我这边暂时无法查询，请稍后再发一次或联系人工客服。";

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public String getBaseUrl()
    {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl)
    {
        this.baseUrl = baseUrl;
    }

    public String getApiKey()
    {
        return apiKey;
    }

    public void setApiKey(String apiKey)
    {
        this.apiKey = apiKey;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public String getSystemPrompt()
    {
        return systemPrompt;
    }

    public void setSystemPrompt(String systemPrompt)
    {
        this.systemPrompt = systemPrompt;
    }

    public String getFallbackReply()
    {
        return fallbackReply;
    }

    public void setFallbackReply(String fallbackReply)
    {
        this.fallbackReply = fallbackReply;
    }

    public boolean isConfigured()
    {
        return apiKey != null && !apiKey.isEmpty();
    }
}
