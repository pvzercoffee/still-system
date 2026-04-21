package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 每日出摊打卡对象 biz_checkin
 * 
 * @author ruoyi
 * @date 2026-04-21
 */
public class BizCheckin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 打卡ID */
    private Long checkinId;

    /** 摊贩ID */
    @Excel(name = "摊贩ID")
    private Long vendorId;

    /** 所在围栏ID */
    @Excel(name = "所在围栏ID")
    private Long fenceId;

    /** 打卡纬度 */
    @Excel(name = "打卡纬度")
    private BigDecimal latitude;

    /** 打卡经度 */
    @Excel(name = "打卡经度")
    private BigDecimal longitude;

    /** 状态（0合规经营 1越界违规） */
    @Excel(name = "状态", readConverterExp = "0=合规经营,1=越界违规")
    private String status;

    /** 收摊时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "收摊时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkoutTime;

    public void setCheckinId(Long checkinId) 
    {
        this.checkinId = checkinId;
    }

    public Long getCheckinId() 
    {
        return checkinId;
    }

    public void setVendorId(Long vendorId) 
    {
        this.vendorId = vendorId;
    }

    public Long getVendorId() 
    {
        return vendorId;
    }

    public void setFenceId(Long fenceId) 
    {
        this.fenceId = fenceId;
    }

    public Long getFenceId() 
    {
        return fenceId;
    }

    public void setLatitude(BigDecimal latitude) 
    {
        this.latitude = latitude;
    }

    public BigDecimal getLatitude() 
    {
        return latitude;
    }

    public void setLongitude(BigDecimal longitude) 
    {
        this.longitude = longitude;
    }

    public BigDecimal getLongitude() 
    {
        return longitude;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setCheckoutTime(Date checkoutTime) 
    {
        this.checkoutTime = checkoutTime;
    }

    public Date getCheckoutTime() 
    {
        return checkoutTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("checkinId", getCheckinId())
            .append("vendorId", getVendorId())
            .append("fenceId", getFenceId())
            .append("latitude", getLatitude())
            .append("longitude", getLongitude())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("checkoutTime", getCheckoutTime())
            .toString();
    }
}
