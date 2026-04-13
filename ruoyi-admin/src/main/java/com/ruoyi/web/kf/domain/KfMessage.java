package com.ruoyi.web.kf.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客服消息流水
 */
public class KfMessage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long messageId;
    private String msgid;
    private String externalUserid;
    private String openKfid;
    /** 1 客户 2 企业 */
    private String direction;
    private String msgtype;
    private String content;
    private Long sendTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Long getMessageId()
    {
        return messageId;
    }

    public void setMessageId(Long messageId)
    {
        this.messageId = messageId;
    }

    public String getMsgid()
    {
        return msgid;
    }

    public void setMsgid(String msgid)
    {
        this.msgid = msgid;
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

    public String getDirection()
    {
        return direction;
    }

    public void setDirection(String direction)
    {
        this.direction = direction;
    }

    public String getMsgtype()
    {
        return msgtype;
    }

    public void setMsgtype(String msgtype)
    {
        this.msgtype = msgtype;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Long getSendTime()
    {
        return sendTime;
    }

    public void setSendTime(Long sendTime)
    {
        this.sendTime = sendTime;
    }

    @Override
    public Date getCreateTime()
    {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
}
