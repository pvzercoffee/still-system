package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.BizFence;

/**
 * 电子围栏区域Service接口
 * 
 * @author ruoyi
 * @date 2026-04-21
 */
public interface IBizFenceService 
{
    /**
     * 查询电子围栏区域
     * 
     * @param fenceId 电子围栏区域主键
     * @return 电子围栏区域
     */
    public BizFence selectBizFenceByFenceId(Long fenceId);

    /**
     * 查询电子围栏区域列表
     * 
     * @param bizFence 电子围栏区域
     * @return 电子围栏区域集合
     */
    public List<BizFence> selectBizFenceList(BizFence bizFence);

    /**
     * 新增电子围栏区域
     * 
     * @param bizFence 电子围栏区域
     * @return 结果
     */
    public int insertBizFence(BizFence bizFence);

    /**
     * 修改电子围栏区域
     * 
     * @param bizFence 电子围栏区域
     * @return 结果
     */
    public int updateBizFence(BizFence bizFence);

    /**
     * 批量删除电子围栏区域
     * 
     * @param fenceIds 需要删除的电子围栏区域主键集合
     * @return 结果
     */
    public int deleteBizFenceByFenceIds(Long[] fenceIds);

    /**
     * 删除电子围栏区域信息
     * 
     * @param fenceId 电子围栏区域主键
     * @return 结果
     */
    public int deleteBizFenceByFenceId(Long fenceId);
}
