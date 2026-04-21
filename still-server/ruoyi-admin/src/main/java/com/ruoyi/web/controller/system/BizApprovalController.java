package com.ruoyi.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.BizVendor;
import com.ruoyi.system.domain.BizVolunteer;
import com.ruoyi.system.service.IBizVendorService;
import com.ruoyi.system.service.IBizVolunteerService;

/**
 * 商贩/志愿者审核接口（管理端）
 */
@RestController
@RequestMapping("/system/approval")
public class BizApprovalController extends BaseController
{
    @Autowired
    private IBizVendorService bizVendorService;

    @Autowired
    private IBizVolunteerService bizVolunteerService;

    /**
     * 创建商贩（管理端）
     */
    @PostMapping("/vendors")
    public AjaxResult createVendor(@RequestBody BizVendor bizVendor)
    {
        return toAjax(bizVendorService.insertBizVendor(bizVendor));
    }

    /**
     * 同意商贩入驻
     */
    @PatchMapping("/vendors/{vendorId}/approve")
    public AjaxResult approveVendor(@PathVariable Long vendorId)
    {
        BizVendor vendor = bizVendorService.selectBizVendorByVendorId(vendorId);
        if (vendor == null)
        {
            return AjaxResult.error("商贩不存在");
        }
        vendor.setStatus("1");
        return toAjax(bizVendorService.updateBizVendor(vendor));
    }

    /**
     * 同意志愿者申请
     */
    @PatchMapping("/volunteers/{volunteerId}/approve")
    public AjaxResult approveVolunteer(@PathVariable Long volunteerId)
    {
        BizVolunteer volunteer = bizVolunteerService.selectBizVolunteerByVolunteerId(volunteerId);
        if (volunteer == null)
        {
            return AjaxResult.error("志愿者申请不存在");
        }
        volunteer.setStatus("1");
        return toAjax(bizVolunteerService.updateBizVolunteer(volunteer));
    }
}

