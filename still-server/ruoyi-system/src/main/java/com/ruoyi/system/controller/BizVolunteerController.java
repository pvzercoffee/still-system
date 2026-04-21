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
import com.ruoyi.system.domain.BizVolunteer;
import com.ruoyi.system.service.IBizVolunteerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 志愿者申请与档案Controller
 * 
 * @author ruoyi
 * @date 2026-04-21
 */
@RestController
@RequestMapping("/system/volunteer")
public class BizVolunteerController extends BaseController
{
    @Autowired
    private IBizVolunteerService bizVolunteerService;

    /**
     * 查询志愿者申请与档案列表
     */
    @PreAuthorize("@ss.hasPermi('system:volunteer:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizVolunteer bizVolunteer)
    {
        startPage();
        List<BizVolunteer> list = bizVolunteerService.selectBizVolunteerList(bizVolunteer);
        return getDataTable(list);
    }

    /**
     * 导出志愿者申请与档案列表
     */
    @PreAuthorize("@ss.hasPermi('system:volunteer:export')")
    @Log(title = "志愿者申请与档案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizVolunteer bizVolunteer)
    {
        List<BizVolunteer> list = bizVolunteerService.selectBizVolunteerList(bizVolunteer);
        ExcelUtil<BizVolunteer> util = new ExcelUtil<BizVolunteer>(BizVolunteer.class);
        util.exportExcel(response, list, "志愿者申请与档案数据");
    }

    /**
     * 获取志愿者申请与档案详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:volunteer:query')")
    @GetMapping(value = "/{volunteerId}")
    public AjaxResult getInfo(@PathVariable("volunteerId") Long volunteerId)
    {
        return success(bizVolunteerService.selectBizVolunteerByVolunteerId(volunteerId));
    }

    /**
     * 新增志愿者申请与档案
     */
    @PreAuthorize("@ss.hasPermi('system:volunteer:add')")
    @Log(title = "志愿者申请与档案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizVolunteer bizVolunteer)
    {
        return toAjax(bizVolunteerService.insertBizVolunteer(bizVolunteer));
    }

    /**
     * 修改志愿者申请与档案
     */
    @PreAuthorize("@ss.hasPermi('system:volunteer:edit')")
    @Log(title = "志愿者申请与档案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizVolunteer bizVolunteer)
    {
        return toAjax(bizVolunteerService.updateBizVolunteer(bizVolunteer));
    }

    /**
     * 删除志愿者申请与档案
     */
    @PreAuthorize("@ss.hasPermi('system:volunteer:remove')")
    @Log(title = "志愿者申请与档案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{volunteerIds}")
    public AjaxResult remove(@PathVariable Long[] volunteerIds)
    {
        return toAjax(bizVolunteerService.deleteBizVolunteerByVolunteerIds(volunteerIds));
    }
}
