package com.ruoyi.web.creb.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 螃蟹养殖池信息对象 crab_pool
 * 
 * @author chendong
 * @date 2025-05-31
 */
public class CrabPool extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 养殖池ID */
    private Long poolId;

    /** 养殖池名称 */
    @Excel(name = "养殖池名称")
    private String poolName;

    /** 养殖池面积(平方米) */
    @Excel(name = "养殖池面积(平方米)")
    private BigDecimal poolArea;

    /** 养殖池深度(米) */
    @Excel(name = "养殖池深度(米)")
    private BigDecimal poolDepth;

    /** 养殖池类型（0室内 1室外） */
    @Excel(name = "养殖池类型", readConverterExp = "0=室内,1=室外")
    private String poolType;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public void setPoolId(Long poolId) 
    {
        this.poolId = poolId;
    }

    public Long getPoolId() 
    {
        return poolId;
    }

    public void setPoolName(String poolName) 
    {
        this.poolName = poolName;
    }

    public String getPoolName() 
    {
        return poolName;
    }

    public void setPoolArea(BigDecimal poolArea) 
    {
        this.poolArea = poolArea;
    }

    public BigDecimal getPoolArea() 
    {
        return poolArea;
    }

    public void setPoolDepth(BigDecimal poolDepth) 
    {
        this.poolDepth = poolDepth;
    }

    public BigDecimal getPoolDepth() 
    {
        return poolDepth;
    }

    public void setPoolType(String poolType) 
    {
        this.poolType = poolType;
    }

    public String getPoolType() 
    {
        return poolType;
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
            .append("poolId", getPoolId())
            .append("poolName", getPoolName())
            .append("poolArea", getPoolArea())
            .append("poolDepth", getPoolDepth())
            .append("poolType", getPoolType())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
