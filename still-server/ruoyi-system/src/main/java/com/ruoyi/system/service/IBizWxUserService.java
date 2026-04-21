package com.ruoyi.system.service;

import com.ruoyi.system.domain.BizWxUser;

/**
 * 小程序微信用户Service接口
 */
public interface IBizWxUserService
{
    BizWxUser selectBizWxUserByWxUserId(Long wxUserId);

    BizWxUser selectBizWxUserByOpenid(String openid);

    int insertBizWxUser(BizWxUser bizWxUser);

    int updateBizWxUser(BizWxUser bizWxUser);
}

