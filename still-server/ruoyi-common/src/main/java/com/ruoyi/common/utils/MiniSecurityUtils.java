package com.ruoyi.common.utils;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.model.MiniLoginUser;
import com.ruoyi.common.exception.ServiceException;

/**
 * 小程序端上下文工具
 */
public class MiniSecurityUtils
{
    private static final ThreadLocal<MiniLoginUser> MINI_CONTEXT = new ThreadLocal<>();

    public static void setLoginUser(MiniLoginUser loginUser)
    {
        MINI_CONTEXT.set(loginUser);
    }

    public static MiniLoginUser getLoginUser()
    {
        MiniLoginUser loginUser = MINI_CONTEXT.get();
        if (loginUser == null)
        {
            throw new ServiceException("小程序用户未登录", HttpStatus.UNAUTHORIZED);
        }
        return loginUser;
    }

    public static Long getWxUserId()
    {
        return getLoginUser().getWxUserId();
    }

    public static void clear()
    {
        MINI_CONTEXT.remove();
    }
}

