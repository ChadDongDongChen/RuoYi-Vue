package com.ruoyi.web.photo.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

public class PhotoUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;
    private String openid;
    private String unionId;
    private String nickname;
    private String avatarUrl;
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    public void setId(Long id) { this.id = id; }
    public Long getId() { return id; }

    public void setOpenid(String openid) { this.openid = openid; }
    public String getOpenid() { return openid; }

    public void setUnionId(String unionId) { this.unionId = unionId; }
    public String getUnionId() { return unionId; }

    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getNickname() { return nickname; }

    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public String getAvatarUrl() { return avatarUrl; }

    public void setStatus(Integer status) { this.status = status; }
    public Integer getStatus() { return status; }

    public void setLastLoginTime(Date lastLoginTime) { this.lastLoginTime = lastLoginTime; }
    public Date getLastLoginTime() { return lastLoginTime; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("openid", getOpenid())
            .append("nickname", getNickname())
            .append("status", getStatus())
            .toString();
    }
}
