package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.BizCheckinMapper;
import com.ruoyi.system.domain.BizCheckin;
import com.ruoyi.system.service.IBizCheckinService;

/**
 * 每日出摊打卡Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-21
 */
@Service
public class BizCheckinServiceImpl implements IBizCheckinService 
{
    @Autowired
    private BizCheckinMapper bizCheckinMapper;

    /**
     * 查询每日出摊打卡
     * 
     * @param checkinId 每日出摊打卡主键
     * @return 每日出摊打卡
     */
    @Override
    public BizCheckin selectBizCheckinByCheckinId(Long checkinId)
    {
        return bizCheckinMapper.selectBizCheckinByCheckinId(checkinId);
    }

    /**
     * 查询每日出摊打卡列表
     * 
     * @param bizCheckin 每日出摊打卡
     * @return 每日出摊打卡
     */
    @Override
    public List<BizCheckin> selectBizCheckinList(BizCheckin bizCheckin)
    {
        return bizCheckinMapper.selectBizCheckinList(bizCheckin);
    }

    /**
     * 新增每日出摊打卡
     * 
     * @param bizCheckin 每日出摊打卡
     * @return 结果
     */
    @Override
    public int insertBizCheckin(BizCheckin bizCheckin)
    {
        bizCheckin.setCreateTime(DateUtils.getNowDate());
        return bizCheckinMapper.insertBizCheckin(bizCheckin);
    }

    /**
     * 修改每日出摊打卡
     * 
     * @param bizCheckin 每日出摊打卡
     * @return 结果
     */
    @Override
    public int updateBizCheckin(BizCheckin bizCheckin)
    {
        return bizCheckinMapper.updateBizCheckin(bizCheckin);
    }

    /**
     * 批量删除每日出摊打卡
     * 
     * @param checkinIds 需要删除的每日出摊打卡主键
     * @return 结果
     */
    @Override
    public int deleteBizCheckinByCheckinIds(Long[] checkinIds)
    {
        return bizCheckinMapper.deleteBizCheckinByCheckinIds(checkinIds);
    }

    /**
     * 删除每日出摊打卡信息
     * 
     * @param checkinId 每日出摊打卡主键
     * @return 结果
     */
    @Override
    public int deleteBizCheckinByCheckinId(Long checkinId)
    {
        return bizCheckinMapper.deleteBizCheckinByCheckinId(checkinId);
    }
}
