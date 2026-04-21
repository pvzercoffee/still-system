package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.BizFence;

/**
 * 电子围栏区域Mapper接口
 * 
 * @author ruoyi
 * @date 2026-04-21
 */
public interface BizFenceMapper 
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
     * 删除电子围栏区域
     * 
     * @param fenceId 电子围栏区域主键
     * @return 结果
     */
    public int deleteBizFenceByFenceId(Long fenceId);

    /**
     * 批量删除电子围栏区域
     * 
     * @param fenceIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizFenceByFenceIds(Long[] fenceIds);
}
