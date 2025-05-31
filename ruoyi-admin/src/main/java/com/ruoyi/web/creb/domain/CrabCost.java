package com.ruoyi.web.creb.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 养殖成本记录对象 crab_cost
 * 
 * @author chendong
 * @date 2025-05-31
 */
public class CrabCost extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long recordId;

    /** 批次ID */
    @Excel(name = "批次ID")
    private Long batchId;

    /** 成本类型 */
    @Excel(name = "成本类型")
    private String costType;

    /** 成本金额 */
    @Excel(name = "成本金额")
    private BigDecimal costAmount;

    /** 发生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date costDate;

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

    public void setCostType(String costType) 
    {
        this.costType = costType;
    }

    public String getCostType() 
    {
        return costType;
    }

    public void setCostAmount(BigDecimal costAmount) 
    {
        this.costAmount = costAmount;
    }

    public BigDecimal getCostAmount() 
    {
        return costAmount;
    }

    public void setCostDate(Date costDate) 
    {
        this.costDate = costDate;
    }

    public Date getCostDate() 
    {
        return costDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("batchId", getBatchId())
            .append("costType", getCostType())
            .append("costAmount", getCostAmount())
            .append("costDate", getCostDate())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("remark", getRemark())
            .toString();
    }
}
