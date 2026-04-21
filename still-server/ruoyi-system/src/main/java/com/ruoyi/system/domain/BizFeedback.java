package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 留言与反馈对象 biz_feedback
 * 
 * @author ruoyi
 * @date 2026-04-21
 */
public class BizFeedback extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 留言ID */
    private Long feedbackId;

    /** 所属摊贩ID */
    @Excel(name = "所属摊贩ID")
    private Long vendorId;

    /** 留言学生ID(可为空代表匿名) */
    @Excel(name = "留言学生ID(可为空代表匿名)")
    private Long userId;

    /** 评分(1-5星) */
    @Excel(name = "评分(1-5星)")
    private Long rating;

    /** 留言内容 */
    @Excel(name = "留言内容")
    private String content;

    /** 摊主回复内容 */
    @Excel(name = "摊主回复内容")
    private String replyContent;

    /** 回复时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "回复时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date replyTime;

    public void setFeedbackId(Long feedbackId) 
    {
        this.feedbackId = feedbackId;
    }

    public Long getFeedbackId() 
    {
        return feedbackId;
    }

    public void setVendorId(Long vendorId) 
    {
        this.vendorId = vendorId;
    }

    public Long getVendorId() 
    {
        return vendorId;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setRating(Long rating) 
    {
        this.rating = rating;
    }

    public Long getRating() 
    {
        return rating;
    }

    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    public void setReplyContent(String replyContent) 
    {
        this.replyContent = replyContent;
    }

    public String getReplyContent() 
    {
        return replyContent;
    }

    public void setReplyTime(Date replyTime) 
    {
        this.replyTime = replyTime;
    }

    public Date getReplyTime() 
    {
        return replyTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("feedbackId", getFeedbackId())
            .append("vendorId", getVendorId())
            .append("userId", getUserId())
            .append("rating", getRating())
            .append("content", getContent())
            .append("replyContent", getReplyContent())
            .append("replyTime", getReplyTime())
            .append("createTime", getCreateTime())
            .toString();
    }
}
