package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.BizFenceMapper;
import com.ruoyi.system.domain.BizFence;
import com.ruoyi.system.service.IBizFenceService;

/**
 * 电子围栏区域Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-21
 */
@Service
public class BizFenceServiceImpl implements IBizFenceService 
{
    @Autowired
    private BizFenceMapper bizFenceMapper;

    /**
     * 查询电子围栏区域
     * 
     * @param fenceId 电子围栏区域主键
     * @return 电子围栏区域
     */
    @Override
    public BizFence selectBizFenceByFenceId(Long fenceId)
    {
        return bizFenceMapper.selectBizFenceByFenceId(fenceId);
    }

    /**
     * 查询电子围栏区域列表
     * 
     * @param bizFence 电子围栏区域
     * @return 电子围栏区域
     */
    @Override
    public List<BizFence> selectBizFenceList(BizFence bizFence)
    {
        return bizFenceMapper.selectBizFenceList(bizFence);
    }

    /**
     * 新增电子围栏区域
     * 
     * @param bizFence 电子围栏区域
     * @return 结果
     */
    @Override
    public int insertBizFence(BizFence bizFence)
    {
        bizFence.setCreateTime(DateUtils.getNowDate());
        return bizFenceMapper.insertBizFence(bizFence);
    }

    /**
     * 修改电子围栏区域
     * 
     * @param bizFence 电子围栏区域
     * @return 结果
     */
    @Override
    public int updateBizFence(BizFence bizFence)
    {
        bizFence.setUpdateTime(DateUtils.getNowDate());
        return bizFenceMapper.updateBizFence(bizFence);
    }

    /**
     * 批量删除电子围栏区域
     * 
     * @param fenceIds 需要删除的电子围栏区域主键
     * @return 结果
     */
    @Override
    public int deleteBizFenceByFenceIds(Long[] fenceIds)
    {
        return bizFenceMapper.deleteBizFenceByFenceIds(fenceIds);
    }

    /**
     * 删除电子围栏区域信息
     * 
     * @param fenceId 电子围栏区域主键
     * @return 结果
     */
    @Override
    public int deleteBizFenceByFenceId(Long fenceId)
    {
        return bizFenceMapper.deleteBizFenceByFenceId(fenceId);
    }
}
