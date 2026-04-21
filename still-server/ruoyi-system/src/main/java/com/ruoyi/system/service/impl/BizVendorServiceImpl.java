package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.BizVendorMapper;
import com.ruoyi.system.domain.BizVendor;
import com.ruoyi.system.service.IBizVendorService;

/**
 * 摊贩档案Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-21
 */
@Service
public class BizVendorServiceImpl implements IBizVendorService 
{
    @Autowired
    private BizVendorMapper bizVendorMapper;

    /**
     * 查询摊贩档案
     * 
     * @param vendorId 摊贩档案主键
     * @return 摊贩档案
     */
    @Override
    public BizVendor selectBizVendorByVendorId(Long vendorId)
    {
        return bizVendorMapper.selectBizVendorByVendorId(vendorId);
    }

    /**
     * 查询摊贩档案列表
     * 
     * @param bizVendor 摊贩档案
     * @return 摊贩档案
     */
    @Override
    public List<BizVendor> selectBizVendorList(BizVendor bizVendor)
    {
        return bizVendorMapper.selectBizVendorList(bizVendor);
    }

    /**
     * 新增摊贩档案
     * 
     * @param bizVendor 摊贩档案
     * @return 结果
     */
    @Override
    public int insertBizVendor(BizVendor bizVendor)
    {
        bizVendor.setCreateTime(DateUtils.getNowDate());
        return bizVendorMapper.insertBizVendor(bizVendor);
    }

    /**
     * 修改摊贩档案
     * 
     * @param bizVendor 摊贩档案
     * @return 结果
     */
    @Override
    public int updateBizVendor(BizVendor bizVendor)
    {
        bizVendor.setUpdateTime(DateUtils.getNowDate());
        return bizVendorMapper.updateBizVendor(bizVendor);
    }

    /**
     * 批量删除摊贩档案
     * 
     * @param vendorIds 需要删除的摊贩档案主键
     * @return 结果
     */
    @Override
    public int deleteBizVendorByVendorIds(Long[] vendorIds)
    {
        return bizVendorMapper.deleteBizVendorByVendorIds(vendorIds);
    }

    /**
     * 删除摊贩档案信息
     * 
     * @param vendorId 摊贩档案主键
     * @return 结果
     */
    @Override
    public int deleteBizVendorByVendorId(Long vendorId)
    {
        return bizVendorMapper.deleteBizVendorByVendorId(vendorId);
    }
}
