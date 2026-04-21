package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 志愿者申请与档案对象 biz_volunteer
 * 
 * @author ruoyi
 * @date 2026-04-21
 */
public class BizVolunteer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 志愿者ID */
    private Long volunteerId;

    /** 关联sys_user(审核通过后生成并绑定) */
    @Excel(name = "关联sys_user(审核通过后生成并绑定)")
    private Long userId;

    /** 学生姓名 */
    @Excel(name = "学生姓名")
    private String studentName;

    /** 学号 */
    @Excel(name = "学号")
    private String studentId;

    /** 所属学院(如: 财经学院) */
    @Excel(name = "所属学院(如: 财经学院)")
    private String college;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phone;

    /** 状态（0待审 1通过 2驳回 3清退） */
    @Excel(name = "状态", readConverterExp = "0=待审,1=通过,2=驳回,3=清退")
    private String status;

    /** 累计巡查次数(用于评优) */
    @Excel(name = "累计巡查次数(用于评优)")
    private Long patrolCount;

    public void setVolunteerId(Long volunteerId) 
    {
        this.volunteerId = volunteerId;
    }

    public Long getVolunteerId() 
    {
        return volunteerId;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setStudentName(String studentName) 
    {
        this.studentName = studentName;
    }

    public String getStudentName() 
    {
        return studentName;
    }

    public void setStudentId(String studentId) 
    {
        this.studentId = studentId;
    }

    public String getStudentId() 
    {
        return studentId;
    }

    public void setCollege(String college) 
    {
        this.college = college;
    }

    public String getCollege() 
    {
        return college;
    }

    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setPatrolCount(Long patrolCount) 
    {
        this.patrolCount = patrolCount;
    }

    public Long getPatrolCount() 
    {
        return patrolCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("volunteerId", getVolunteerId())
            .append("userId", getUserId())
            .append("studentName", getStudentName())
            .append("studentId", getStudentId())
            .append("college", getCollege())
            .append("phone", getPhone())
            .append("status", getStatus())
            .append("patrolCount", getPatrolCount())
            .append("createTime", getCreateTime())
            .append("remark", getRemark())
            .toString();
    }
}
