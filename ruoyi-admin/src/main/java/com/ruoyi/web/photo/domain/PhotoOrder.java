package com.ruoyi.web.photo.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class PhotoOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "订单号")
    private String orderNo;

    @Excel(name = "用户ID")
    private Long userId;

    @Excel(name = "原始图片URL")
    private String originalImageUrl;

    @Excel(name = "规格ID")
    private Long specId;

    @Excel(name = "规格名称")
    private String specName;

    @Excel(name = "背景色")
    private String background;

    @Excel(name = "美颜级别")
    private String beauty;

    @Excel(name = "正装选项")
    private String suit;

    @Excel(name = "结果图片URL")
    private String resultImageUrl;

    @Excel(name = "订单状态")
    private String status;

    @Excel(name = "支付金额")
    private BigDecimal payAmount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    @Excel(name = "支付方式")
    private String payType;

    public void setId(Long id) { this.id = id; }
    public Long getId() { return id; }

    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }
    public String getOrderNo() { return orderNo; }

    public void setUserId(Long userId) { this.userId = userId; }
    public Long getUserId() { return userId; }

    public void setOriginalImageUrl(String originalImageUrl) { this.originalImageUrl = originalImageUrl; }
    public String getOriginalImageUrl() { return originalImageUrl; }

    public void setSpecId(Long specId) { this.specId = specId; }
    public Long getSpecId() { return specId; }

    public void setSpecName(String specName) { this.specName = specName; }
    public String getSpecName() { return specName; }

    public void setBackground(String background) { this.background = background; }
    public String getBackground() { return background; }

    public void setBeauty(String beauty) { this.beauty = beauty; }
    public String getBeauty() { return beauty; }

    public void setSuit(String suit) { this.suit = suit; }
    public String getSuit() { return suit; }

    public void setResultImageUrl(String resultImageUrl) { this.resultImageUrl = resultImageUrl; }
    public String getResultImageUrl() { return resultImageUrl; }

    public void setStatus(String status) { this.status = status; }
    public String getStatus() { return status; }

    public void setPayAmount(BigDecimal payAmount) { this.payAmount = payAmount; }
    public BigDecimal getPayAmount() { return payAmount; }

    public void setPayTime(Date payTime) { this.payTime = payTime; }
    public Date getPayTime() { return payTime; }

    public void setPayType(String payType) { this.payType = payType; }
    public String getPayType() { return payType; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderNo", getOrderNo())
            .append("userId", getUserId())
            .append("specName", getSpecName())
            .append("status", getStatus())
            .append("payAmount", getPayAmount())
            .append("createTime", getCreateTime())
            .toString();
    }
}
