package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.BizVendor;

/**
 * 摊贩档案Service接口
 * 
 * @author ruoyi
 * @date 2026-04-21
 */
public interface IBizVendorService 
{
    /**
     * 查询摊贩档案
     * 
     * @param vendorId 摊贩档案主键
     * @return 摊贩档案
     */
    public BizVendor selectBizVendorByVendorId(Long vendorId);

    /**
     * 查询摊贩档案列表
     * 
     * @param bizVendor 摊贩档案
     * @return 摊贩档案集合
     */
    public List<BizVendor> selectBizVendorList(BizVendor bizVendor);

    /**
     * 新增摊贩档案
     * 
     * @param bizVendor 摊贩档案
     * @return 结果
     */
    public int insertBizVendor(BizVendor bizVendor);

    /**
     * 修改摊贩档案
     * 
     * @param bizVendor 摊贩档案
     * @return 结果
     */
    public int updateBizVendor(BizVendor bizVendor);

    /**
     * 批量删除摊贩档案
     * 
     * @param vendorIds 需要删除的摊贩档案主键集合
     * @return 结果
     */
    public int deleteBizVendorByVendorIds(Long[] vendorIds);

    /**
     * 删除摊贩档案信息
     * 
     * @param vendorId 摊贩档案主键
     * @return 结果
     */
    public int deleteBizVendorByVendorId(Long vendorId);
}
