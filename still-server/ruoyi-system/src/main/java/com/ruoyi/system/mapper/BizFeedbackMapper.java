package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.BizFeedback;

/**
 * 留言与反馈Mapper接口
 * 
 * @author ruoyi
 * @date 2026-04-21
 */
public interface BizFeedbackMapper 
{
    /**
     * 查询留言与反馈
     * 
     * @param feedbackId 留言与反馈主键
     * @return 留言与反馈
     */
    public BizFeedback selectBizFeedbackByFeedbackId(Long feedbackId);

    /**
     * 查询留言与反馈列表
     * 
     * @param bizFeedback 留言与反馈
     * @return 留言与反馈集合
     */
    public List<BizFeedback> selectBizFeedbackList(BizFeedback bizFeedback);

    /**
     * 新增留言与反馈
     * 
     * @param bizFeedback 留言与反馈
     * @return 结果
     */
    public int insertBizFeedback(BizFeedback bizFeedback);

    /**
     * 修改留言与反馈
     * 
     * @param bizFeedback 留言与反馈
     * @return 结果
     */
    public int updateBizFeedback(BizFeedback bizFeedback);

    /**
     * 删除留言与反馈
     * 
     * @param feedbackId 留言与反馈主键
     * @return 结果
     */
    public int deleteBizFeedbackByFeedbackId(Long feedbackId);

    /**
     * 批量删除留言与反馈
     * 
     * @param feedbackIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizFeedbackByFeedbackIds(Long[] feedbackIds);
}
