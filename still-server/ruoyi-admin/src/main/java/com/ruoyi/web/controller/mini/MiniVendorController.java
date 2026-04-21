package com.ruoyi.web.controller.mini;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.MiniSecurityUtils;
import com.ruoyi.system.domain.BizFeedback;
import com.ruoyi.system.domain.BizVendor;
import com.ruoyi.system.domain.BizWxUser;
import com.ruoyi.system.service.IBizFeedbackService;
import com.ruoyi.system.service.IBizVendorService;
import com.ruoyi.system.service.IBizWxUserService;

/**
 * 小程序端商贩接口
 */
@RestController
@RequestMapping("/mini/vendors")
public class MiniVendorController extends BaseController
{
    @Autowired
    private IBizVendorService bizVendorService;

    @Autowired
    private IBizFeedbackService bizFeedbackService;

    @Autowired
    private IBizWxUserService bizWxUserService;

    /**
     * 获取商贩基本信息列表
     */
    @GetMapping
    public TableDataInfo list()
    {
        startPage();
        BizVendor query = new BizVendor();
        query.setStatus("1");
        query.setDelFlag("0");
        List<BizVendor> list = bizVendorService.selectBizVendorList(query);
        return getDataTable(list);
    }

    /**
     * 获取指定id商贩信息
     */
    @GetMapping("/{vendorId}")
    public AjaxResult getInfo(@PathVariable Long vendorId)
    {
        BizVendor vendor = bizVendorService.selectBizVendorByVendorId(vendorId);
        if (vendor == null)
        {
            return AjaxResult.error("商贩不存在");
        }
        return AjaxResult.success(vendor);
    }

    /**
     * 获取商贩评论（需求5/10）
     */
    @GetMapping("/{vendorId}/comments")
    public TableDataInfo comments(@PathVariable Long vendorId)
    {
        startPage();
        BizFeedback query = new BizFeedback();
        query.setVendorId(vendorId);
        List<BizFeedback> list = bizFeedbackService.selectBizFeedbackList(query);
        return getDataTable(list);
    }

    /**
     * 获取商贩评论（兼容query方式）
     */
    @GetMapping("/comments")
    public TableDataInfo commentsByQuery(@RequestParam Long vendorId)
    {
        return comments(vendorId);
    }

    /**
     * 注册为商贩（提交入驻申请）
     */
    @PostMapping("/apply")
    public AjaxResult applyVendor(@RequestBody BizVendor bizVendor)
    {
        Long wxUserId = MiniSecurityUtils.getWxUserId();
        BizVendor query = new BizVendor();
        query.setUserId(wxUserId);
        List<BizVendor> mine = bizVendorService.selectBizVendorList(query);
        if (!mine.isEmpty())
        {
            return AjaxResult.error("已提交过商贩档案");
        }

        bizVendor.setVendorId(null);
        bizVendor.setUserId(wxUserId);
        bizVendor.setStatus("0");
        bizVendor.setDelFlag("0");
        int rows = bizVendorService.insertBizVendor(bizVendor);

        BizWxUser wxUser = bizWxUserService.selectBizWxUserByWxUserId(wxUserId);
        if (wxUser != null)
        {
            wxUser.setUserType("1");
            bizWxUserService.updateBizWxUser(wxUser);
        }
        return toAjax(rows);
    }

    /**
     * 商贩修改自己的信息
     */
    @PutMapping("/me")
    public AjaxResult updateMyVendor(@RequestBody BizVendor bizVendor)
    {
        Long wxUserId = MiniSecurityUtils.getWxUserId();
        BizVendor query = new BizVendor();
        query.setUserId(wxUserId);
        List<BizVendor> mine = bizVendorService.selectBizVendorList(query);
        if (mine.isEmpty())
        {
            return AjaxResult.error("未找到当前登录用户的商贩档案");
        }
        BizVendor current = mine.get(0);

        bizVendor.setVendorId(current.getVendorId());
        bizVendor.setUserId(wxUserId);
        // 用户自改资料后回到待审，防止绕过审核
        bizVendor.setStatus("0");
        return toAjax(bizVendorService.updateBizVendor(bizVendor));
    }

    /**
     * 对商贩留言与评分
     */
    @PostMapping("/{vendorId}/comments")
    public AjaxResult addComment(@PathVariable Long vendorId, @RequestBody BizFeedback bizFeedback)
    {
        BizVendor vendor = bizVendorService.selectBizVendorByVendorId(vendorId);
        if (vendor == null)
        {
            return AjaxResult.error("商贩不存在");
        }
        Long wxUserId = MiniSecurityUtils.getWxUserId();
        bizFeedback.setFeedbackId(null);
        bizFeedback.setVendorId(vendorId);
        bizFeedback.setUserId(wxUserId);
        return toAjax(bizFeedbackService.insertBizFeedback(bizFeedback));
    }
}

