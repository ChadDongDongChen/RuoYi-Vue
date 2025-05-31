package com.ruoyi.web.creb.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常预警记录对象 crab_alert
 * 
 * @author chendong
 * @date 2025-05-31
 */
public class CrabAlert extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 预警ID */
    private Long alertId;

    /** 养殖池ID */
    @Excel(name = "养殖池ID")
    private Long poolId;

    /** 设备ID */
    @Excel(name = "设备ID")
    private Long deviceId;

    /** 预警类型（1温度异常 2水质异常 3设备故障） */
    @Excel(name = "预警类型", readConverterExp = "1=温度异常,2=水质异常,3=设备故障")
    private String alertType;

    /** 预警值 */
    @Excel(name = "预警值")
    private BigDecimal alertValue;

    /** 预警级别（0一般 1重要 2紧急） */
    @Excel(name = "预警级别", readConverterExp = "0=一般,1=重要,2=紧急")
    private String alertLevel;

    /** 处理状态（0未处理 1已处理） */
    @Excel(name = "处理状态", readConverterExp = "0=未处理,1=已处理")
    private String alertStatus;

    /** 预警时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预警时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date alertTime;

    /** 处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date handleTime;

    /** 处理人 */
    @Excel(name = "处理人")
    private String handleBy;

    /** 处理结果 */
    @Excel(name = "处理结果")
    private String handleResult;

    public void setAlertId(Long alertId) 
    {
        this.alertId = alertId;
    }

    public Long getAlertId() 
    {
        return alertId;
    }

    public void setPoolId(Long poolId) 
    {
        this.poolId = poolId;
    }

    public Long getPoolId() 
    {
        return poolId;
    }

    public void setDeviceId(Long deviceId) 
    {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() 
    {
        return deviceId;
    }

    public void setAlertType(String alertType) 
    {
        this.alertType = alertType;
    }

    public String getAlertType() 
    {
        return alertType;
    }

    public void setAlertValue(BigDecimal alertValue) 
    {
        this.alertValue = alertValue;
    }

    public BigDecimal getAlertValue() 
    {
        return alertValue;
    }

    public void setAlertLevel(String alertLevel) 
    {
        this.alertLevel = alertLevel;
    }

    public String getAlertLevel() 
    {
        return alertLevel;
    }

    public void setAlertStatus(String alertStatus) 
    {
        this.alertStatus = alertStatus;
    }

    public String getAlertStatus() 
    {
        return alertStatus;
    }

    public void setAlertTime(Date alertTime) 
    {
        this.alertTime = alertTime;
    }

    public Date getAlertTime() 
    {
        return alertTime;
    }

    public void setHandleTime(Date handleTime) 
    {
        this.handleTime = handleTime;
    }

    public Date getHandleTime() 
    {
        return handleTime;
    }

    public void setHandleBy(String handleBy) 
    {
        this.handleBy = handleBy;
    }

    public String getHandleBy() 
    {
        return handleBy;
    }

    public void setHandleResult(String handleResult) 
    {
        this.handleResult = handleResult;
    }

    public String getHandleResult() 
    {
        return handleResult;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("alertId", getAlertId())
            .append("poolId", getPoolId())
            .append("deviceId", getDeviceId())
            .append("alertType", getAlertType())
            .append("alertValue", getAlertValue())
            .append("alertLevel", getAlertLevel())
            .append("alertStatus", getAlertStatus())
            .append("alertTime", getAlertTime())
            .append("handleTime", getHandleTime())
            .append("handleBy", getHandleBy())
            .append("handleResult", getHandleResult())
            .append("createTime", getCreateTime())
            .toString();
    }
}
