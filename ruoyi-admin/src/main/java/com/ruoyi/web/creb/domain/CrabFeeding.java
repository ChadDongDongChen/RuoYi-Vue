package com.ruoyi.web.creb.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 投喂记录对象 crab_feeding
 * 
 * @author chendong
 * @date 2025-05-31
 */
public class CrabFeeding extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long recordId;

    /** 养殖池ID */
    @Excel(name = "养殖池ID")
    private Long poolId;

    /** 饲料类型 */
    @Excel(name = "饲料类型")
    private String feedType;

    /** 投喂量(kg) */
    @Excel(name = "投喂量(kg)")
    private BigDecimal feedAmount;

    /** 投喂时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "投喂时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date feedTime;

    /** 操作人 */
    @Excel(name = "操作人")
    private String operator;

    public void setRecordId(Long recordId) 
    {
        this.recordId = recordId;
    }

    public Long getRecordId() 
    {
        return recordId;
    }

    public void setPoolId(Long poolId) 
    {
        this.poolId = poolId;
    }

    public Long getPoolId() 
    {
        return poolId;
    }

    public void setFeedType(String feedType) 
    {
        this.feedType = feedType;
    }

    public String getFeedType() 
    {
        return feedType;
    }

    public void setFeedAmount(BigDecimal feedAmount) 
    {
        this.feedAmount = feedAmount;
    }

    public BigDecimal getFeedAmount() 
    {
        return feedAmount;
    }

    public void setFeedTime(Date feedTime) 
    {
        this.feedTime = feedTime;
    }

    public Date getFeedTime() 
    {
        return feedTime;
    }

    public void setOperator(String operator) 
    {
        this.operator = operator;
    }

    public String getOperator() 
    {
        return operator;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("poolId", getPoolId())
            .append("feedType", getFeedType())
            .append("feedAmount", getFeedAmount())
            .append("feedTime", getFeedTime())
            .append("operator", getOperator())
            .append("createTime", getCreateTime())
            .append("remark", getRemark())
            .toString();
    }
}
