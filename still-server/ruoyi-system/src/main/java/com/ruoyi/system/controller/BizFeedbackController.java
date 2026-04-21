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
import com.ruoyi.system.domain.BizFeedback;
import com.ruoyi.system.service.IBizFeedbackService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 留言与反馈Controller
 * 
 * @author ruoyi
 * @date 2026-04-21
 */
@RestController
@RequestMapping("/system/feedback")
public class BizFeedbackController extends BaseController
{
    @Autowired
    private IBizFeedbackService bizFeedbackService;

    /**
     * 查询留言与反馈列表
     */
    @PreAuthorize("@ss.hasPermi('system:feedback:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizFeedback bizFeedback)
    {
        startPage();
        List<BizFeedback> list = bizFeedbackService.selectBizFeedbackList(bizFeedback);
        return getDataTable(list);
    }

    /**
     * 导出留言与反馈列表
     */
    @PreAuthorize("@ss.hasPermi('system:feedback:export')")
    @Log(title = "留言与反馈", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizFeedback bizFeedback)
    {
        List<BizFeedback> list = bizFeedbackService.selectBizFeedbackList(bizFeedback);
        ExcelUtil<BizFeedback> util = new ExcelUtil<BizFeedback>(BizFeedback.class);
        util.exportExcel(response, list, "留言与反馈数据");
    }

    /**
     * 获取留言与反馈详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:feedback:query')")
    @GetMapping(value = "/{feedbackId}")
    public AjaxResult getInfo(@PathVariable("feedbackId") Long feedbackId)
    {
        return success(bizFeedbackService.selectBizFeedbackByFeedbackId(feedbackId));
    }

    /**
     * 新增留言与反馈
     */
    @PreAuthorize("@ss.hasPermi('system:feedback:add')")
    @Log(title = "留言与反馈", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizFeedback bizFeedback)
    {
        return toAjax(bizFeedbackService.insertBizFeedback(bizFeedback));
    }

    /**
     * 修改留言与反馈
     */
    @PreAuthorize("@ss.hasPermi('system:feedback:edit')")
    @Log(title = "留言与反馈", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizFeedback bizFeedback)
    {
        return toAjax(bizFeedbackService.updateBizFeedback(bizFeedback));
    }

    /**
     * 删除留言与反馈
     */
    @PreAuthorize("@ss.hasPermi('system:feedback:remove')")
    @Log(title = "留言与反馈", businessType = BusinessType.DELETE)
	@DeleteMapping("/{feedbackIds}")
    public AjaxResult remove(@PathVariable Long[] feedbackIds)
    {
        return toAjax(bizFeedbackService.deleteBizFeedbackByFeedbackIds(feedbackIds));
    }
}
