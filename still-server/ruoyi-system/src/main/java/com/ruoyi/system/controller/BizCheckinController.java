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
import com.ruoyi.system.domain.BizCheckin;
import com.ruoyi.system.service.IBizCheckinService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 每日出摊打卡Controller
 * 
 * @author ruoyi
 * @date 2026-04-21
 */
@RestController
@RequestMapping("/system/checkin")
public class BizCheckinController extends BaseController
{
    @Autowired
    private IBizCheckinService bizCheckinService;

    /**
     * 查询每日出摊打卡列表
     */
    @PreAuthorize("@ss.hasPermi('system:checkin:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizCheckin bizCheckin)
    {
        startPage();
        List<BizCheckin> list = bizCheckinService.selectBizCheckinList(bizCheckin);
        return getDataTable(list);
    }

    /**
     * 导出每日出摊打卡列表
     */
    @PreAuthorize("@ss.hasPermi('system:checkin:export')")
    @Log(title = "每日出摊打卡", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizCheckin bizCheckin)
    {
        List<BizCheckin> list = bizCheckinService.selectBizCheckinList(bizCheckin);
        ExcelUtil<BizCheckin> util = new ExcelUtil<BizCheckin>(BizCheckin.class);
        util.exportExcel(response, list, "每日出摊打卡数据");
    }

    /**
     * 获取每日出摊打卡详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:checkin:query')")
    @GetMapping(value = "/{checkinId}")
    public AjaxResult getInfo(@PathVariable("checkinId") Long checkinId)
    {
        return success(bizCheckinService.selectBizCheckinByCheckinId(checkinId));
    }

    /**
     * 新增每日出摊打卡
     */
    @PreAuthorize("@ss.hasPermi('system:checkin:add')")
    @Log(title = "每日出摊打卡", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizCheckin bizCheckin)
    {
        return toAjax(bizCheckinService.insertBizCheckin(bizCheckin));
    }

    /**
     * 修改每日出摊打卡
     */
    @PreAuthorize("@ss.hasPermi('system:checkin:edit')")
    @Log(title = "每日出摊打卡", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizCheckin bizCheckin)
    {
        return toAjax(bizCheckinService.updateBizCheckin(bizCheckin));
    }

    /**
     * 删除每日出摊打卡
     */
    @PreAuthorize("@ss.hasPermi('system:checkin:remove')")
    @Log(title = "每日出摊打卡", businessType = BusinessType.DELETE)
	@DeleteMapping("/{checkinIds}")
    public AjaxResult remove(@PathVariable Long[] checkinIds)
    {
        return toAjax(bizCheckinService.deleteBizCheckinByCheckinIds(checkinIds));
    }
}
