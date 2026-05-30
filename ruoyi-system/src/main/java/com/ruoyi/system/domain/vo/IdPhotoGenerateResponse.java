package com.ruoyi.system.domain.vo;

import java.math.BigDecimal;

/**
 * AI证件照生成响应
 */
public class IdPhotoGenerateResponse
{
    /** 订单号 */
    private String orderNo;

    /** 结果图URL */
    private String resultUrl;

    /** 原图URL */
    private String originalUrl;

    /** 规格名称 */
    private String specName;

    /** 应付金额 */
    private BigDecimal amount;

    /** 订单状态 */
    private String status;

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getResultUrl()
    {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl)
    {
        this.resultUrl = resultUrl;
    }

    public String getOriginalUrl()
    {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl)
    {
        this.originalUrl = originalUrl;
    }

    public String getSpecName()
    {
        return specName;
    }

    public void setSpecName(String specName)
    {
        this.specName = specName;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}
