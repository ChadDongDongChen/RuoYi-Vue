package com.ruoyi.web.kf.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wecom.kf")
public class WecomKfProperties
{
    private boolean enabled = true;
    private String corpId = "";
    private String secret = "";
    private String token = "";
    private String encodingAesKey = "";

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public String getCorpId()
    {
        return corpId;
    }

    public void setCorpId(String corpId)
    {
        this.corpId = corpId;
    }

    public String getSecret()
    {
        return secret;
    }

    public void setSecret(String secret)
    {
        this.secret = secret;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String getEncodingAesKey()
    {
        return encodingAesKey;
    }

    public void setEncodingAesKey(String encodingAesKey)
    {
        this.encodingAesKey = encodingAesKey;
    }

    public boolean isConfigured()
    {
        return corpId != null && !corpId.isEmpty()
                && secret != null && !secret.isEmpty()
                && token != null && !token.isEmpty()
                && encodingAesKey != null && encodingAesKey.length() == 43;
    }
}
