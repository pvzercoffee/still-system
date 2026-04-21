package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.BizViolation;

/**
 * 巡查与违规记录Service接口
 * 
 * @author ruoyi
 * @date 2026-04-21
 */
public interface IBizViolationService 
{
    /**
     * 查询巡查与违规记录
     * 
     * @param violationId 巡查与违规记录主键
     * @return 巡查与违规记录
     */
    public BizViolation selectBizViolationByViolationId(Long violationId);

    /**
     * 查询巡查与违规记录列表
     * 
     * @param bizViolation 巡查与违规记录
     * @return 巡查与违规记录集合
     */
    public List<BizViolation> selectBizViolationList(BizViolation bizViolation);

    /**
     * 新增巡查与违规记录
     * 
     * @param bizViolation 巡查与违规记录
     * @return 结果
     */
    public int insertBizViolation(BizViolation bizViolation);

    /**
     * 修改巡查与违规记录
     * 
     * @param bizViolation 巡查与违规记录
     * @return 结果
     */
    public int updateBizViolation(BizViolation bizViolation);

    /**
     * 批量删除巡查与违规记录
     * 
     * @param violationIds 需要删除的巡查与违规记录主键集合
     * @return 结果
     */
    public int deleteBizViolationByViolationIds(Long[] violationIds);

    /**
     * 删除巡查与违规记录信息
     * 
     * @param violationId 巡查与违规记录主键
     * @return 结果
     */
    public int deleteBizViolationByViolationId(Long violationId);
}
