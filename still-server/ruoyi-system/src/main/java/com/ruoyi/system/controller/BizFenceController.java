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
import com.ruoyi.system.domain.BizFence;
import com.ruoyi.system.service.IBizFenceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 电子围栏区域Controller
 * 
 * @author ruoyi
 * @date 2026-04-21
 */
@RestController
@RequestMapping("/system/fence")
public class BizFenceController extends BaseController
{
    @Autowired
    private IBizFenceService bizFenceService;

    /**
     * 查询电子围栏区域列表
     */
    @PreAuthorize("@ss.hasPermi('system:fence:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizFence bizFence)
    {
        startPage();
        List<BizFence> list = bizFenceService.selectBizFenceList(bizFence);
        return getDataTable(list);
    }

    /**
     * 导出电子围栏区域列表
     */
    @PreAuthorize("@ss.hasPermi('system:fence:export')")
    @Log(title = "电子围栏区域", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizFence bizFence)
    {
        List<BizFence> list = bizFenceService.selectBizFenceList(bizFence);
        ExcelUtil<BizFence> util = new ExcelUtil<BizFence>(BizFence.class);
        util.exportExcel(response, list, "电子围栏区域数据");
    }

    /**
     * 获取电子围栏区域详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:fence:query')")
    @GetMapping(value = "/{fenceId}")
    public AjaxResult getInfo(@PathVariable("fenceId") Long fenceId)
    {
        return success(bizFenceService.selectBizFenceByFenceId(fenceId));
    }

    /**
     * 新增电子围栏区域
     */
    @PreAuthorize("@ss.hasPermi('system:fence:add')")
    @Log(title = "电子围栏区域", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizFence bizFence)
    {
        return toAjax(bizFenceService.insertBizFence(bizFence));
    }

    /**
     * 修改电子围栏区域
     */
    @PreAuthorize("@ss.hasPermi('system:fence:edit')")
    @Log(title = "电子围栏区域", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizFence bizFence)
    {
        return toAjax(bizFenceService.updateBizFence(bizFence));
    }

    /**
     * 删除电子围栏区域
     */
    @PreAuthorize("@ss.hasPermi('system:fence:remove')")
    @Log(title = "电子围栏区域", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fenceIds}")
    public AjaxResult remove(@PathVariable Long[] fenceIds)
    {
        return toAjax(bizFenceService.deleteBizFenceByFenceIds(fenceIds));
    }
}
