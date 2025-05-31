package com.ruoyi.web.creb.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 养殖池维护记录对象 crab_maintenance
 * 
 * @author chendong
 * @date 2025-05-31
 */
public class CrabMaintenance extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long recordId;

    /** 养殖池ID */
    @Excel(name = "养殖池ID")
    private Long poolId;

    /** 维护类型 */
    @Excel(name = "维护类型")
    private String maintenanceType;

    /** 维护日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "维护日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date maintenanceDate;

    /** 操作人 */
    @Excel(name = "操作人")
    private String operator;

    /** 维护费用 */
    @Excel(name = "维护费用")
    private BigDecimal cost;

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

    public void setMaintenanceType(String maintenanceType) 
    {
        this.maintenanceType = maintenanceType;
    }

    public String getMaintenanceType() 
    {
        return maintenanceType;
    }

    public void setMaintenanceDate(Date maintenanceDate) 
    {
        this.maintenanceDate = maintenanceDate;
    }

    public Date getMaintenanceDate() 
    {
        return maintenanceDate;
    }

    public void setOperator(String operator) 
    {
        this.operator = operator;
    }

    public String getOperator() 
    {
        return operator;
    }

    public void setCost(BigDecimal cost) 
    {
        this.cost = cost;
    }

    public BigDecimal getCost() 
    {
        return cost;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("poolId", getPoolId())
            .append("maintenanceType", getMaintenanceType())
            .append("maintenanceDate", getMaintenanceDate())
            .append("operator", getOperator())
            .append("cost", getCost())
            .append("createTime", getCreateTime())
            .append("remark", getRemark())
            .toString();
    }
}
