package com.ruoyi.system.mapper;

import org.apache.ibatis.annotations.Param;
import com.ruoyi.system.domain.BizVendorQrcode;

/**
 * 摊贩二维码Mapper接口
 */
public interface BizVendorQrcodeMapper
{
    /**
     * 按短标识查询二维码
     */
    BizVendorQrcode selectByShortId(String shortId);

    /**
     * 按商贩+目标类型查询二维码
     */
    BizVendorQrcode selectByVendorIdAndTargetType(@Param("vendorId") Long vendorId, @Param("targetType") Integer targetType);

    /**
     * 新增二维码
     */
    int insertBizVendorQrcode(BizVendorQrcode bizVendorQrcode);
}

