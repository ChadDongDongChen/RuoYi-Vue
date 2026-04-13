package com.ruoyi.web.kf.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * sync_msg 游标
 */
public class KfSyncCursor implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String openKfid;
    private String nextCursor;
    private Date updateTime;

    public String getOpenKfid()
    {
        return openKfid;
    }

    public void setOpenKfid(String openKfid)
    {
        this.openKfid = openKfid;
    }

    public String getNextCursor()
    {
        return nextCursor;
    }

    public void setNextCursor(String nextCursor)
    {
        this.nextCursor = nextCursor;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
}
