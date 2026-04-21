package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.BizWxUser;

/**
 * 小程序微信用户Mapper接口
 */
public interface BizWxUserMapper
{
    BizWxUser selectBizWxUserByWxUserId(Long wxUserId);

    BizWxUser selectBizWxUserByOpenid(String openid);

    int insertBizWxUser(BizWxUser bizWxUser);

    int updateBizWxUser(BizWxUser bizWxUser);
}

