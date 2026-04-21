package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 摊贩档案对象 biz_vendor
 * 
 * @author ruoyi
 * @date 2026-04-21
 */
public class BizVendor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 摊贩ID */
    private Long vendorId;

    /** 关联sys_user表的账号ID */
    @Excel(name = "关联sys_user表的账号ID")
    private Long userId;

    /** 摊主真实姓名 */
    @Excel(name = "摊主真实姓名")
    private String vendorName;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phone;

    /** 主营品类(如: 烧烤, 水果) */
    @Excel(name = "主营品类(如: 烧烤, 水果)")
    private String goodsCategory;

    /** 健康证图片地址 */
    @Excel(name = "健康证图片地址")
    private String healthCertUrl;

    /** 信用积分(满分100) */
    @Excel(name = "信用积分(满分100)")
    private Long creditScore;

    /** 审核状态（0待审 1通过 2驳回 3拉黑） */
    @Excel(name = "审核状态", readConverterExp = "0=待审,1=通过,2=驳回,3=拉黑")
    private String status;

    /** 删除标志（0存在 2删除） */
    private String delFlag;

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

    public void setVendorName(String vendorName) 
    {
        this.vendorName = vendorName;
    }

    public String getVendorName() 
    {
        return vendorName;
    }

    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }

    public void setGoodsCategory(String goodsCategory) 
    {
        this.goodsCategory = goodsCategory;
    }

    public String getGoodsCategory() 
    {
        return goodsCategory;
    }

    public void setHealthCertUrl(String healthCertUrl) 
    {
        this.healthCertUrl = healthCertUrl;
    }

    public String getHealthCertUrl() 
    {
        return healthCertUrl;
    }

    public void setCreditScore(Long creditScore) 
    {
        this.creditScore = creditScore;
    }

    public Long getCreditScore() 
    {
        return creditScore;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("vendorId", getVendorId())
            .append("userId", getUserId())
            .append("vendorName", getVendorName())
            .append("phone", getPhone())
            .append("goodsCategory", getGoodsCategory())
            .append("healthCertUrl", getHealthCertUrl())
            .append("creditScore", getCreditScore())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
