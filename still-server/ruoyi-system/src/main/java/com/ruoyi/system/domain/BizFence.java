package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 电子围栏区域对象 biz_fence
 * 
 * @author ruoyi
 * @date 2026-04-21
 */
public class BizFence extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 围栏ID */
    private Long fenceId;

    /** 围栏名称(如: 正门左侧50米A区) */
    @Excel(name = "围栏名称(如: 正门左侧50米A区)")
    private String fenceName;

    /** 多边形坐标集(JSON数组格式存经纬度) */
    @Excel(name = "多边形坐标集(JSON数组格式存经纬度)")
    private String polygonPoints;

    /** 状态（0启用 1停用） */
    @Excel(name = "状态", readConverterExp = "0=启用,1=停用")
    private String status;

    public void setFenceId(Long fenceId) 
    {
        this.fenceId = fenceId;
    }

    public Long getFenceId() 
    {
        return fenceId;
    }

    public void setFenceName(String fenceName) 
    {
        this.fenceName = fenceName;
    }

    public String getFenceName() 
    {
        return fenceName;
    }

    public void setPolygonPoints(String polygonPoints) 
    {
        this.polygonPoints = polygonPoints;
    }

    public String getPolygonPoints() 
    {
        return polygonPoints;
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
            .append("fenceId", getFenceId())
            .append("fenceName", getFenceName())
            .append("polygonPoints", getPolygonPoints())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
