package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.BizFeedbackMapper;
import com.ruoyi.system.domain.BizFeedback;
import com.ruoyi.system.service.IBizFeedbackService;

/**
 * 留言与反馈Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-21
 */
@Service
public class BizFeedbackServiceImpl implements IBizFeedbackService 
{
    @Autowired
    private BizFeedbackMapper bizFeedbackMapper;

    /**
     * 查询留言与反馈
     * 
     * @param feedbackId 留言与反馈主键
     * @return 留言与反馈
     */
    @Override
    public BizFeedback selectBizFeedbackByFeedbackId(Long feedbackId)
    {
        return bizFeedbackMapper.selectBizFeedbackByFeedbackId(feedbackId);
    }

    /**
     * 查询留言与反馈列表
     * 
     * @param bizFeedback 留言与反馈
     * @return 留言与反馈
     */
    @Override
    public List<BizFeedback> selectBizFeedbackList(BizFeedback bizFeedback)
    {
        return bizFeedbackMapper.selectBizFeedbackList(bizFeedback);
    }

    /**
     * 新增留言与反馈
     * 
     * @param bizFeedback 留言与反馈
     * @return 结果
     */
    @Override
    public int insertBizFeedback(BizFeedback bizFeedback)
    {
        bizFeedback.setCreateTime(DateUtils.getNowDate());
        return bizFeedbackMapper.insertBizFeedback(bizFeedback);
    }

    /**
     * 修改留言与反馈
     * 
     * @param bizFeedback 留言与反馈
     * @return 结果
     */
    @Override
    public int updateBizFeedback(BizFeedback bizFeedback)
    {
        return bizFeedbackMapper.updateBizFeedback(bizFeedback);
    }

    /**
     * 批量删除留言与反馈
     * 
     * @param feedbackIds 需要删除的留言与反馈主键
     * @return 结果
     */
    @Override
    public int deleteBizFeedbackByFeedbackIds(Long[] feedbackIds)
    {
        return bizFeedbackMapper.deleteBizFeedbackByFeedbackIds(feedbackIds);
    }

    /**
     * 删除留言与反馈信息
     * 
     * @param feedbackId 留言与反馈主键
     * @return 结果
     */
    @Override
    public int deleteBizFeedbackByFeedbackId(Long feedbackId)
    {
        return bizFeedbackMapper.deleteBizFeedbackByFeedbackId(feedbackId);
    }
}
