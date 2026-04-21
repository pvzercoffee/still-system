package com.ruoyi.system.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.BizVendor;
import com.ruoyi.system.service.IBizVendorService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 摊贩档案Controller
 * 
 * @author ruoyi
 * @date 2026-04-21
 */
@RestController
@RequestMapping("/system/vendor")
public class BizVendorController extends BaseController
{
    @Autowired
    private IBizVendorService bizVendorService;

    /**
     * 查询摊贩档案列表
     */
    @PreAuthorize("@ss.hasPermi('system:vendor:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizVendor bizVendor)
    {
        startPage();
        List<BizVendor> list = bizVendorService.selectBizVendorList(bizVendor);
        return getDataTable(list);
    }

    /**
     * 导出摊贩档案列表
     */
    @PreAuthorize("@ss.hasPermi('system:vendor:export')")
    @Log(title = "摊贩档案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizVendor bizVendor)
    {
        List<BizVendor> list = bizVendorService.selectBizVendorList(bizVendor);
        ExcelUtil<BizVendor> util = new ExcelUtil<BizVendor>(BizVendor.class);
        util.exportExcel(response, list, "摊贩档案数据");
    }

    /**
     * 获取摊贩档案详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:vendor:query')")
    @GetMapping(value = "/{vendorId}")
    public AjaxResult getInfo(@PathVariable("vendorId") Long vendorId)
    {
        return success(bizVendorService.selectBizVendorByVendorId(vendorId));
    }

    /**
     * 新增摊贩档案
     */
    @PreAuthorize("@ss.hasPermi('system:vendor:add')")
    @Log(title = "摊贩档案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizVendor bizVendor)
    {
        return toAjax(bizVendorService.insertBizVendor(bizVendor));
    }

    /**
     * 修改摊贩档案
     */
    @PreAuthorize("@ss.hasPermi('system:vendor:edit')")
    @Log(title = "摊贩档案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizVendor bizVendor)
    {
        return toAjax(bizVendorService.updateBizVendor(bizVendor));
    }

    /**
     * 删除摊贩档案
     */
    @PreAuthorize("@ss.hasPermi('system:vendor:remove')")
    @Log(title = "摊贩档案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{vendorIds}")
    public AjaxResult remove(@PathVariable Long[] vendorIds)
    {
        return toAjax(bizVendorService.deleteBizVendorByVendorIds(vendorIds));
    }
}
