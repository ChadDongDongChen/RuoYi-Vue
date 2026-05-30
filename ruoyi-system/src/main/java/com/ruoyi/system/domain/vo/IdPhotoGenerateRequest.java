package com.ruoyi.system.domain.vo;

/**
 * AI证件照生成请求
 */
public class IdPhotoGenerateRequest
{
    /** 原图URL */
    private String originalUrl;

    /** 规格ID */
    private Long specId;

    /** 背景色: 蓝底/白底/红底 */
    private String background;

    /** 美颜级别: 自然/精致/关闭 */
    private String beauty;

    /** 正装类型: 不开启/男士正装/女士正装 */
    private String suit;

    public String getOriginalUrl()
    {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl)
    {
        this.originalUrl = originalUrl;
    }

    public Long getSpecId()
    {
        return specId;
    }

    public void setSpecId(Long specId)
    {
        this.specId = specId;
    }

    public String getBackground()
    {
        return background;
    }

    public void setBackground(String background)
    {
        this.background = background;
    }

    public String getBeauty()
    {
        return beauty;
    }

    public void setBeauty(String beauty)
    {
        this.beauty = beauty;
    }

    public String getSuit()
    {
        return suit;
    }

    public void setSuit(String suit)
    {
        this.suit = suit;
    }
}
