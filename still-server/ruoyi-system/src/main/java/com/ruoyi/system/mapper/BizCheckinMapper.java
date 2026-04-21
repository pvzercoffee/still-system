package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.BizCheckin;

/**
 * 每日出摊打卡Mapper接口
 * 
 * @author ruoyi
 * @date 2026-04-21
 */
public interface BizCheckinMapper 
{
    /**
     * 查询每日出摊打卡
     * 
     * @param checkinId 每日出摊打卡主键
     * @return 每日出摊打卡
     */
    public BizCheckin selectBizCheckinByCheckinId(Long checkinId);

    /**
     * 查询每日出摊打卡列表
     * 
     * @param bizCheckin 每日出摊打卡
     * @return 每日出摊打卡集合
     */
    public List<BizCheckin> selectBizCheckinList(BizCheckin bizCheckin);

    /**
     * 新增每日出摊打卡
     * 
     * @param bizCheckin 每日出摊打卡
     * @return 结果
     */
    public int insertBizCheckin(BizCheckin bizCheckin);

    /**
     * 修改每日出摊打卡
     * 
     * @param bizCheckin 每日出摊打卡
     * @return 结果
     */
    public int updateBizCheckin(BizCheckin bizCheckin);

    /**
     * 删除每日出摊打卡
     * 
     * @param checkinId 每日出摊打卡主键
     * @return 结果
     */
    public int deleteBizCheckinByCheckinId(Long checkinId);

    /**
     * 批量删除每日出摊打卡
     * 
     * @param checkinIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizCheckinByCheckinIds(Long[] checkinIds);
}
