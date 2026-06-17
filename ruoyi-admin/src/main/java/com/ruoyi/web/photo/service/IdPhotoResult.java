package com.ruoyi.web.photo.service;

/**
 * 证件照生成结果
 */
public class IdPhotoResult
{
    private String imageUrl;
    private String errorReason;

    public IdPhotoResult() {}

    public IdPhotoResult(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public static IdPhotoResult error(String reason)
    {
        IdPhotoResult r = new IdPhotoResult();
        r.errorReason = reason;
        return r;
    }

    public boolean isSuccess()
    {
        return errorReason == null && imageUrl != null;
    }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getErrorReason() { return errorReason; }
    public void setErrorReason(String errorReason) { this.errorReason = errorReason; }
}
