package com.ruoyi.system.domain;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 小程序微信用户对象 biz_wx_user
 */
public class BizWxUser implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Long wxUserId;

    private String openid;

    private String unionid;

    private String nickname;

    private String avatarUrl;

    private String phone;

    /** 身份类型(0:普通学生 1:摊贩 2:志愿者) */
    private String userType;

    /** 账号状态(0正常 1封禁) */
    private String status;

    private Date createTime;

    private Date lastLoginTime;

    public Long getWxUserId()
    {
        return wxUserId;
    }

    public void setWxUserId(Long wxUserId)
    {
        this.wxUserId = wxUserId;
    }

    public String getOpenid()
    {
        return openid;
    }

    public void setOpenid(String openid)
    {
        this.openid = openid;
    }

    public String getUnionid()
    {
        return unionid;
    }

    public void setUnionid(String unionid)
    {
        this.unionid = unionid;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getAvatarUrl()
    {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl)
    {
        this.avatarUrl = avatarUrl;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getUserType()
    {
        return userType;
    }

    public void setUserType(String userType)
    {
        this.userType = userType;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getLastLoginTime()
    {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime)
    {
        this.lastLoginTime = lastLoginTime;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("wxUserId", wxUserId)
            .append("openid", openid)
            .append("unionid", unionid)
            .append("nickname", nickname)
            .append("avatarUrl", avatarUrl)
            .append("phone", phone)
            .append("userType", userType)
            .append("status", status)
            .append("createTime", createTime)
            .append("lastLoginTime", lastLoginTime)
            .toString();
    }
}

