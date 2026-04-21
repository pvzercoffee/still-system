package com.ruoyi.system.service.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.BizWxUser;
import com.ruoyi.system.mapper.BizWxUserMapper;
import com.ruoyi.system.service.IBizWxUserService;

/**
 * 小程序微信用户Service业务层处理
 */
@Service
public class BizWxUserServiceImpl implements IBizWxUserService
{
    @Autowired
    private BizWxUserMapper bizWxUserMapper;

    @Override
    public BizWxUser selectBizWxUserByWxUserId(Long wxUserId)
    {
        return bizWxUserMapper.selectBizWxUserByWxUserId(wxUserId);
    }

    @Override
    public BizWxUser selectBizWxUserByOpenid(String openid)
    {
        return bizWxUserMapper.selectBizWxUserByOpenid(openid);
    }

    @Override
    public int insertBizWxUser(BizWxUser bizWxUser)
    {
        Date now = new Date();
        if (bizWxUser.getCreateTime() == null)
        {
            bizWxUser.setCreateTime(now);
        }
        if (bizWxUser.getLastLoginTime() == null)
        {
            bizWxUser.setLastLoginTime(now);
        }
        return bizWxUserMapper.insertBizWxUser(bizWxUser);
    }

    @Override
    public int updateBizWxUser(BizWxUser bizWxUser)
    {
        return bizWxUserMapper.updateBizWxUser(bizWxUser);
    }
}

