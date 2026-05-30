package com.ruoyi.system.domain.vo;

/**
 * AI证件照支付请求
 */
public class IdPhotoPayRequest
{
    /** 订单号 */
    private String orderNo;

    /** 支付方式: wechat */
    private String payType;

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getPayType()
    {
        return payType;
    }

    public void setPayType(String payType)
    {
        this.payType = payType;
    }
}
