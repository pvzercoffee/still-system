package com.ruoyi.system.service;

import com.ruoyi.system.domain.BizVendorQrcode;

/**
 * 摊贩二维码Service接口
 */
public interface IBizVendorQrcodeService
{
    /**
     * 按商贩ID和目标类型获取二维码，不存在则自动生成
     */
    BizVendorQrcode getOrCreateByVendorIdAndTargetType(Long vendorId, Integer targetType);

    /**
     * 按商贩ID和目标类型查询二维码
     */
    BizVendorQrcode selectByVendorIdAndTargetType(Long vendorId, Integer targetType);
}

