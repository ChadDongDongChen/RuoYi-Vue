package com.ruoyi.web.creb.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 环境监测设备对象 crab_device
 * 
 * @author chendong
 * @date 2025-05-31
 */
public class CrabDevice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备ID */
    private Long deviceId;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /** 设备类型（1温度 2湿度 3水质 4氧气） */
    @Excel(name = "设备类型", readConverterExp = "1=温度,2=湿度,3=水质,4=氧气")
    private String deviceType;

    /** 所属养殖池ID */
    @Excel(name = "所属养殖池ID")
    private Long poolId;

    /** 设备状态（0正常 1故障 2离线） */
    @Excel(name = "设备状态", readConverterExp = "0=正常,1=故障,2=离线")
    private String deviceStatus;

    /** 最后在线时间 时分秒*/

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最后在线时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date lastOnlineTime;

    public void setDeviceId(Long deviceId) 
    {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() 
    {
        return deviceId;
    }

    public void setDeviceName(String deviceName) 
    {
        this.deviceName = deviceName;
    }

    public String getDeviceName() 
    {
        return deviceName;
    }

    public void setDeviceType(String deviceType) 
    {
        this.deviceType = deviceType;
    }

    public String getDeviceType() 
    {
        return deviceType;
    }

    public void setPoolId(Long poolId) 
    {
        this.poolId = poolId;
    }

    public Long getPoolId() 
    {
        return poolId;
    }

    public void setDeviceStatus(String deviceStatus) 
    {
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceStatus() 
    {
        return deviceStatus;
    }

    public void setLastOnlineTime(Date lastOnlineTime) 
    {
        this.lastOnlineTime = lastOnlineTime;
    }

    public Date getLastOnlineTime() 
    {
        return lastOnlineTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("deviceId", getDeviceId())
            .append("deviceName", getDeviceName())
            .append("deviceType", getDeviceType())
            .append("poolId", getPoolId())
            .append("deviceStatus", getDeviceStatus())
            .append("lastOnlineTime", getLastOnlineTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
