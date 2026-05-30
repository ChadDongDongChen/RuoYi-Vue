package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * AI证件照订单表 ai_idphoto_order
 *
 * @author ruoyi
 */
public class AiIdphotoOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    private Long orderId;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderNo;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 规格ID */
    @Excel(name = "规格ID")
    private Long specId;

    /** 规格名称 */
    @Excel(name = "规格名称")
    private String specName;

    /** 背景色 */
    @Excel(name = "背景色")
    private String background;

    /** 美颜级别 */
    @Excel(name = "美颜级别")
    private String beauty;

    /** 正装类型 */
    @Excel(name = "正装类型")
    private String suit;

    /** 原图URL */
    private String originalUrl;

    /** 结果图URL */
    private String resultUrl;

    /** 应付金额 */
    @Excel(name = "应付金额")
    private BigDecimal amount;

    /** 状态(0待支付 1已支付 2已取消 3已退款) */
    @Excel(name = "状态", readConverterExp = "0=待支付,1=已支付,2=已取消,3=已退款")
    private String status;

    /** 支付时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    /** 支付方式 */
    @Excel(name = "支付方式")
    private String payType;

    /** 支付流水号 */
    private String transactionId;

    /** 订单过期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;

    public Long getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Long orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getSpecId()
    {
        return specId;
    }

    public void setSpecId(Long specId)
    {
        this.specId = specId;
    }

    public String getSpecName()
    {
        return specName;
    }

    public void setSpecName(String specName)
    {
        this.specName = specName;
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

    public String getOriginalUrl()
    {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl)
    {
        this.originalUrl = originalUrl;
    }

    public String getResultUrl()
    {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl)
    {
        this.resultUrl = resultUrl;
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

    public Date getPayTime()
    {
        return payTime;
    }

    public void setPayTime(Date payTime)
    {
        this.payTime = payTime;
    }

    public String getPayType()
    {
        return payType;
    }

    public void setPayType(String payType)
    {
        this.payType = payType;
    }

    public String getTransactionId()
    {
        return transactionId;
    }

    public void setTransactionId(String transactionId)
    {
        this.transactionId = transactionId;
    }

    public Date getExpireTime()
    {
        return expireTime;
    }

    public void setExpireTime(Date expireTime)
    {
        this.expireTime = expireTime;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("orderId", getOrderId())
            .append("orderNo", getOrderNo())
            .append("userId", getUserId())
            .append("specId", getSpecId())
            .append("specName", getSpecName())
            .append("background", getBackground())
            .append("beauty", getBeauty())
            .append("suit", getSuit())
            .append("originalUrl", getOriginalUrl())
            .append("resultUrl", getResultUrl())
            .append("amount", getAmount())
            .append("status", getStatus())
            .append("payTime", getPayTime())
            .append("payType", getPayType())
            .append("transactionId", getTransactionId())
            .append("expireTime", getExpireTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
