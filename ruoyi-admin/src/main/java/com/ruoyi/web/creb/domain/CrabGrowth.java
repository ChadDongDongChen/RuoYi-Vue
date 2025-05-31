package com.ruoyi.web.creb.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 螃蟹生长记录对象 crab_growth
 * 
 * @author chendong
 * @date 2025-05-31
 */
public class CrabGrowth extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long recordId;

    /** 批次ID */
    @Excel(name = "批次ID")
    private Long batchId;

    /** 采样数量 */
    @Excel(name = "采样数量")
    private Long sampleCount;

    /** 平均重量(g) */
    @Excel(name = "平均重量(g)")
    private BigDecimal avgWeight;

    /** 死亡率(%) */
    @Excel(name = "死亡率(%)")
    private BigDecimal mortalityRate;

    /** 记录日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "记录日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date recordDate;

    public void setRecordId(Long recordId) 
    {
        this.recordId = recordId;
    }

    public Long getRecordId() 
    {
        return recordId;
    }

    public void setBatchId(Long batchId) 
    {
        this.batchId = batchId;
    }

    public Long getBatchId() 
    {
        return batchId;
    }

    public void setSampleCount(Long sampleCount) 
    {
        this.sampleCount = sampleCount;
    }

    public Long getSampleCount() 
    {
        return sampleCount;
    }

    public void setAvgWeight(BigDecimal avgWeight) 
    {
        this.avgWeight = avgWeight;
    }

    public BigDecimal getAvgWeight() 
    {
        return avgWeight;
    }

    public void setMortalityRate(BigDecimal mortalityRate) 
    {
        this.mortalityRate = mortalityRate;
    }

    public BigDecimal getMortalityRate() 
    {
        return mortalityRate;
    }

    public void setRecordDate(Date recordDate) 
    {
        this.recordDate = recordDate;
    }

    public Date getRecordDate() 
    {
        return recordDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("batchId", getBatchId())
            .append("sampleCount", getSampleCount())
            .append("avgWeight", getAvgWeight())
            .append("mortalityRate", getMortalityRate())
            .append("recordDate", getRecordDate())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("remark", getRemark())
            .toString();
    }
}
