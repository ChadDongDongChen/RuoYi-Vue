package com.ruoyi.web.creb.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 收获记录对象 crab_harvest
 * 
 * @author chendong
 * @date 2025-05-31
 */
public class CrabHarvest extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long recordId;

    /** 批次ID */
    @Excel(name = "批次ID")
    private Long batchId;

    /** 收获日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "收获日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date harvestDate;

    /** 总重量(kg) */
    @Excel(name = "总重量(kg)")
    private BigDecimal totalWeight;

    /** 平均重量(g) */
    @Excel(name = "平均重量(g)")
    private BigDecimal avgWeight;

    /** 存活率(%) */
    @Excel(name = "存活率(%)")
    private BigDecimal survivalRate;

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

    public void setBatchId(Long batchId) 
    {
        this.batchId = batchId;
    }

    public Long getBatchId() 
    {
        return batchId;
    }

    public void setHarvestDate(Date harvestDate) 
    {
        this.harvestDate = harvestDate;
    }

    public Date getHarvestDate() 
    {
        return harvestDate;
    }

    public void setTotalWeight(BigDecimal totalWeight) 
    {
        this.totalWeight = totalWeight;
    }

    public BigDecimal getTotalWeight() 
    {
        return totalWeight;
    }

    public void setAvgWeight(BigDecimal avgWeight) 
    {
        this.avgWeight = avgWeight;
    }

    public BigDecimal getAvgWeight() 
    {
        return avgWeight;
    }

    public void setSurvivalRate(BigDecimal survivalRate) 
    {
        this.survivalRate = survivalRate;
    }

    public BigDecimal getSurvivalRate() 
    {
        return survivalRate;
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
            .append("batchId", getBatchId())
            .append("harvestDate", getHarvestDate())
            .append("totalWeight", getTotalWeight())
            .append("avgWeight", getAvgWeight())
            .append("survivalRate", getSurvivalRate())
            .append("operator", getOperator())
            .append("createTime", getCreateTime())
            .append("remark", getRemark())
            .toString();
    }
}
