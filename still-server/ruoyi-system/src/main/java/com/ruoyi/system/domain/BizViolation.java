package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 巡查与违规记录对象 biz_violation
 * 
 * @author ruoyi
 * @date 2026-04-21
 */
public class BizViolation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long violationId;

    /** 违规摊贩ID */
    @Excel(name = "违规摊贩ID")
    private Long vendorId;

    /** 巡查员/志愿者ID(关联sys_user) */
    @Excel(name = "巡查员/志愿者ID(关联sys_user)")
    private Long inspectorId;

    /** 违规类型(占道/卫生差/无证) */
    @Excel(name = "违规类型(占道/卫生差/无证)")
    private String violationType;

    /** 现场照片(多张逗号分隔) */
    @Excel(name = "现场照片(多张逗号分隔)")
    private String photoUrl;

    /** 扣除积分值 */
    @Excel(name = "扣除积分值")
    private Long deductScore;

    /** 处理状态（0待处理 1已扣分确认 2驳回撤销） */
    @Excel(name = "处理状态", readConverterExp = "0=待处理,1=已扣分确认,2=驳回撤销")
    private String status;

    public void setViolationId(Long violationId) 
    {
        this.violationId = violationId;
    }

    public Long getViolationId() 
    {
        return violationId;
    }

    public void setVendorId(Long vendorId) 
    {
        this.vendorId = vendorId;
    }

    public Long getVendorId() 
    {
        return vendorId;
    }

    public void setInspectorId(Long inspectorId) 
    {
        this.inspectorId = inspectorId;
    }

    public Long getInspectorId() 
    {
        return inspectorId;
    }

    public void setViolationType(String violationType) 
    {
        this.violationType = violationType;
    }

    public String getViolationType() 
    {
        return violationType;
    }

    public void setPhotoUrl(String photoUrl) 
    {
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() 
    {
        return photoUrl;
    }

    public void setDeductScore(Long deductScore) 
    {
        this.deductScore = deductScore;
    }

    public Long getDeductScore() 
    {
        return deductScore;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("violationId", getViolationId())
            .append("vendorId", getVendorId())
            .append("inspectorId", getInspectorId())
            .append("violationType", getViolationType())
            .append("photoUrl", getPhotoUrl())
            .append("deductScore", getDeductScore())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
