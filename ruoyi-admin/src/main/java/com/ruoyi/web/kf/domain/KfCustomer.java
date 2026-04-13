package com.ruoyi.web.kf.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 微信客服客户
 */
public class KfCustomer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long customerId;

    @Excel(name = "external_userid")
    private String externalUserid;

    @Excel(name = "open_kfid")
    private String openKfid;

    private String nickname;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date firstContactTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastActiveTime;

    public Long getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(Long customerId)
    {
        this.customerId = customerId;
    }

    public String getExternalUserid()
    {
        return externalUserid;
    }

    public void setExternalUserid(String externalUserid)
    {
        this.externalUserid = externalUserid;
    }

    public String getOpenKfid()
    {
        return openKfid;
    }

    public void setOpenKfid(String openKfid)
    {
        this.openKfid = openKfid;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public Date getFirstContactTime()
    {
        return firstContactTime;
    }

    public void setFirstContactTime(Date firstContactTime)
    {
        this.firstContactTime = firstContactTime;
    }

    public Date getLastActiveTime()
    {
        return lastActiveTime;
    }

    public void setLastActiveTime(Date lastActiveTime)
    {
        this.lastActiveTime = lastActiveTime;
    }
}
