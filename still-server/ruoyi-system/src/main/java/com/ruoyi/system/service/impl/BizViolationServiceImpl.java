package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.BizViolationMapper;
import com.ruoyi.system.domain.BizViolation;
import com.ruoyi.system.service.IBizViolationService;

/**
 * 巡查与违规记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-21
 */
@Service
public class BizViolationServiceImpl implements IBizViolationService 
{
    @Autowired
    private BizViolationMapper bizViolationMapper;

    /**
     * 查询巡查与违规记录
     * 
     * @param violationId 巡查与违规记录主键
     * @return 巡查与违规记录
     */
    @Override
    public BizViolation selectBizViolationByViolationId(Long violationId)
    {
        return bizViolationMapper.selectBizViolationByViolationId(violationId);
    }

    /**
     * 查询巡查与违规记录列表
     * 
     * @param bizViolation 巡查与违规记录
     * @return 巡查与违规记录
     */
    @Override
    public List<BizViolation> selectBizViolationList(BizViolation bizViolation)
    {
        return bizViolationMapper.selectBizViolationList(bizViolation);
    }

    /**
     * 新增巡查与违规记录
     * 
     * @param bizViolation 巡查与违规记录
     * @return 结果
     */
    @Override
    public int insertBizViolation(BizViolation bizViolation)
    {
        bizViolation.setCreateTime(DateUtils.getNowDate());
        return bizViolationMapper.insertBizViolation(bizViolation);
    }

    /**
     * 修改巡查与违规记录
     * 
     * @param bizViolation 巡查与违规记录
     * @return 结果
     */
    @Override
    public int updateBizViolation(BizViolation bizViolation)
    {
        bizViolation.setUpdateTime(DateUtils.getNowDate());
        return bizViolationMapper.updateBizViolation(bizViolation);
    }

    /**
     * 批量删除巡查与违规记录
     * 
     * @param violationIds 需要删除的巡查与违规记录主键
     * @return 结果
     */
    @Override
    public int deleteBizViolationByViolationIds(Long[] violationIds)
    {
        return bizViolationMapper.deleteBizViolationByViolationIds(violationIds);
    }

    /**
     * 删除巡查与违规记录信息
     * 
     * @param violationId 巡查与违规记录主键
     * @return 结果
     */
    @Override
    public int deleteBizViolationByViolationId(Long violationId)
    {
        return bizViolationMapper.deleteBizViolationByViolationId(violationId);
    }
}
