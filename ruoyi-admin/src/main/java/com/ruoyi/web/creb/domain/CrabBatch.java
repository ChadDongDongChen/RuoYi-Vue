package com.ruoyi.web.creb.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 螃蟹批次信息对象 crab_batch
 * 
 * @author chendong
 * @date 2025-05-31
 */
public class CrabBatch extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 批次ID */
    private Long batchId;

    /** 批次名称 */
    @Excel(name = "批次名称")
    private String batchName;

    /** 养殖池ID */
    @Excel(name = "养殖池ID")
    private Long poolId;

    /** 螃蟹品种 */
    @Excel(name = "螃蟹品种")
    private String crabType;

    /** 初始数量 */
    @Excel(name = "初始数量")
    private Long initialCount;

    /** 初始平均重量(g) */
    @Excel(name = "初始平均重量(g)")
    private BigDecimal initialWeight;

    /** 开始养殖日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始养殖日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 预计收获日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预计收获日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expectedDate;

    /** 状态（0养殖中 1已收获） */
    @Excel(name = "状态", readConverterExp = "0=养殖中,1=已收获")
    private String status;

    public void setBatchId(Long batchId) 
    {
        this.batchId = batchId;
    }

    public Long getBatchId() 
    {
        return batchId;
    }

    public void setBatchName(String batchName) 
    {
        this.batchName = batchName;
    }

    public String getBatchName() 
    {
        return batchName;
    }

    public void setPoolId(Long poolId) 
    {
        this.poolId = poolId;
    }

    public Long getPoolId() 
    {
        return poolId;
    }

    public void setCrabType(String crabType) 
    {
        this.crabType = crabType;
    }

    public String getCrabType() 
    {
        return crabType;
    }

    public void setInitialCount(Long initialCount) 
    {
        this.initialCount = initialCount;
    }

    public Long getInitialCount() 
    {
        return initialCount;
    }

    public void setInitialWeight(BigDecimal initialWeight) 
    {
        this.initialWeight = initialWeight;
    }

    public BigDecimal getInitialWeight() 
    {
        return initialWeight;
    }

    public void setStartDate(Date startDate) 
    {
        this.startDate = startDate;
    }

    public Date getStartDate() 
    {
        return startDate;
    }

    public void setExpectedDate(Date expectedDate) 
    {
        this.expectedDate = expectedDate;
    }

    public Date getExpectedDate() 
    {
        return expectedDate;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("batchId", getBatchId())
            .append("batchName", getBatchName())
            .append("poolId", getPoolId())
            .append("crabType", getCrabType())
            .append("initialCount", getInitialCount())
            .append("initialWeight", getInitialWeight())
            .append("startDate", getStartDate())
            .append("expectedDate", getExpectedDate())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
