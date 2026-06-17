package com.ruoyi.web.photo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "photo")
public class PhotoAiProperties
{
    private Wx wx = new Wx();
    private Ai ai = new Ai();
    private Pay pay = new Pay();

    public Wx getWx() { return wx; }
    public void setWx(Wx wx) { this.wx = wx; }

    public Ai getAi() { return ai; }
    public void setAi(Ai ai) { this.ai = ai; }

    public Pay getPay() { return pay; }
    public void setPay(Pay pay) { this.pay = pay; }

    public boolean isWxConfigured()
    {
        return wx.getAppId() != null && !wx.getAppId().isEmpty()
            && wx.getAppSecret() != null && !wx.getAppSecret().isEmpty();
    }

    public boolean isAiConfigured()
    {
        return ai.getBaseUrl() != null && !ai.getBaseUrl().isEmpty()
            && ai.getApiKey() != null && !ai.getApiKey().isEmpty();
    }

    public static class Wx
    {
        private String appId;
        private String appSecret;

        public String getAppId() { return appId; }
        public void setAppId(String appId) { this.appId = appId; }

        public String getAppSecret() { return appSecret; }
        public void setAppSecret(String appSecret) { this.appSecret = appSecret; }
    }

    public static class Ai
    {
        private String baseUrl;
        private String apiKey;
        private String model;
        private String segmentModel;

        public String getBaseUrl() { return baseUrl; }
        public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }

        public String getApiKey() { return apiKey; }
        public void setApiKey(String apiKey) { this.apiKey = apiKey; }

        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }

        public String getSegmentModel() { return segmentModel; }
        public void setSegmentModel(String segmentModel) { this.segmentModel = segmentModel; }
    }

    public static class Pay
    {
        private Boolean mock = false;

        public Boolean getMock() { return mock; }
        public void setMock(Boolean mock) { this.mock = mock; }
    }
}
