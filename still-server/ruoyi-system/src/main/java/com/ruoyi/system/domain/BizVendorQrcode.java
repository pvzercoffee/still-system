package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 摊贩二维码对象 biz_vendor_qrcode
 */
public class BizVendorQrcode extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 二维码短标识(主键) */
    private String shortId;

    /** 跳转类型（1:商贩主页,2:支付页,3:投诉页,4:监管页） */
    @Excel(name = "跳转类型")
    private Integer targetType;

    /** 对应的商贩ID */
    @Excel(name = "对应的商贩ID")
    private Long vendorId;

    /** 二维码状态（1:正常,0:失效/封禁） */
    @Excel(name = "二维码状态")
    private Integer status;

    public String getShortId()
    {
        return shortId;
    }

    public void setShortId(String shortId)
    {
        this.shortId = shortId;
    }

    public Integer getTargetType()
    {
        return targetType;
    }

    public void setTargetType(Integer targetType)
    {
        this.targetType = targetType;
    }

    public Long getVendorId()
    {
        return vendorId;
    }

    public void setVendorId(Long vendorId)
    {
        this.vendorId = vendorId;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("shortId", getShortId())
            .append("targetType", getTargetType())
            .append("vendorId", getVendorId())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

