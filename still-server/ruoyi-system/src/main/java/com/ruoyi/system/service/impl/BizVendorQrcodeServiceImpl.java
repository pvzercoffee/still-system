package com.ruoyi.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.domain.BizVendorQrcode;
import com.ruoyi.system.mapper.BizVendorQrcodeMapper;
import com.ruoyi.system.service.IBizVendorQrcodeService;

/**
 * 摊贩二维码Service业务层处理
 */
@Service
public class BizVendorQrcodeServiceImpl implements IBizVendorQrcodeService
{
    private static final int DEFAULT_TARGET_TYPE = 1;
    private static final int DEFAULT_STATUS = 1;

    @Autowired
    private BizVendorQrcodeMapper bizVendorQrcodeMapper;

    @Override
    public BizVendorQrcode getOrCreateByVendorIdAndTargetType(Long vendorId, Integer targetType)
    {
        Integer resolvedTargetType = (targetType == null) ? DEFAULT_TARGET_TYPE : targetType;
        BizVendorQrcode existing = selectByVendorIdAndTargetType(vendorId, resolvedTargetType);
        if (existing != null)
        {
            return existing;
        }

        // 理论上UUID冲突概率极低，仍保留重试逻辑保障主键唯一性
        for (int i = 0; i < 3; i++)
        {
            BizVendorQrcode qrcode = new BizVendorQrcode();
            qrcode.setShortId(IdUtils.fastSimpleUUID());
            qrcode.setVendorId(vendorId);
            qrcode.setTargetType(resolvedTargetType);
            qrcode.setStatus(DEFAULT_STATUS);
            qrcode.setCreateTime(DateUtils.getNowDate());
            qrcode.setUpdateTime(DateUtils.getNowDate());

            try
            {
                bizVendorQrcodeMapper.insertBizVendorQrcode(qrcode);
                return qrcode;
            }
            catch (DuplicateKeyException e)
            {
                // 短码冲突时重试
            }
        }

        // 并发下若其他请求已创建，直接返回最终记录
        BizVendorQrcode finalRecord = selectByVendorIdAndTargetType(vendorId, resolvedTargetType);
        if (finalRecord != null)
        {
            return finalRecord;
        }

        throw new IllegalStateException("二维码生成失败，请稍后重试");
    }

    @Override
    public BizVendorQrcode selectByVendorIdAndTargetType(Long vendorId, Integer targetType)
    {
        Integer resolvedTargetType = (targetType == null) ? DEFAULT_TARGET_TYPE : targetType;
        return bizVendorQrcodeMapper.selectByVendorIdAndTargetType(vendorId, resolvedTargetType);
    }
}

