package com.ruoyi.web.creb.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 环境数据记录对象 crab_environment
 * 
 * @author chendong
 * @date 2025-05-31
 */
public class CrabEnvironment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 数据ID */
    private Long dataId;

    /** 设备ID */
    @Excel(name = "设备ID")
    private Long deviceId;

    /** 养殖池ID */
    @Excel(name = "养殖池ID")
    private Long poolId;

    /** 数据类型（1温度 2湿度 3水质 4氧气） */
    @Excel(name = "数据类型", readConverterExp = "1=温度,2=湿度,3=水质,4=氧气")
    private String dataType;

    /** 数据值 */
    @Excel(name = "数据值")
    private BigDecimal dataValue;

    /** 采集时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "采集时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date collectTime;

    public void setDataId(Long dataId) 
    {
        this.dataId = dataId;
    }

    public Long getDataId() 
    {
        return dataId;
    }

    public void setDeviceId(Long deviceId) 
    {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() 
    {
        return deviceId;
    }

    public void setPoolId(Long poolId) 
    {
        this.poolId = poolId;
    }

    public Long getPoolId() 
    {
        return poolId;
    }

    public void setDataType(String dataType) 
    {
        this.dataType = dataType;
    }

    public String getDataType() 
    {
        return dataType;
    }

    public void setDataValue(BigDecimal dataValue) 
    {
        this.dataValue = dataValue;
    }

    public BigDecimal getDataValue() 
    {
        return dataValue;
    }

    public void setCollectTime(Date collectTime) 
    {
        this.collectTime = collectTime;
    }

    public Date getCollectTime() 
    {
        return collectTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("dataId", getDataId())
            .append("deviceId", getDeviceId())
            .append("poolId", getPoolId())
            .append("dataType", getDataType())
            .append("dataValue", getDataValue())
            .append("collectTime", getCollectTime())
            .append("createTime", getCreateTime())
            .toString();
    }
}
