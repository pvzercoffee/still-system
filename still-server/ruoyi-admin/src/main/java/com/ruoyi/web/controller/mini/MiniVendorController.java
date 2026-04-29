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
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.BizFeedback;
import com.ruoyi.system.domain.BizVendor;
import com.ruoyi.system.domain.BizVendorQrcode;
import com.ruoyi.system.domain.BizWxUser;
import com.ruoyi.system.service.IBizFeedbackService;
import com.ruoyi.system.service.IBizVendorService;
import com.ruoyi.system.service.IBizVendorQrcodeService;
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

    @Autowired
    private IBizVendorQrcodeService bizVendorQrcodeService;

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
     * 查询当前登录用户的摊贩档案详情
     * 用于前端在编辑前回显表单数据
     */
    @GetMapping("/me")
    public AjaxResult getMyVendor()
    {
        Long wxUserId = MiniSecurityUtils.getWxUserId();
        BizVendor query = new BizVendor();
        query.setUserId(wxUserId);
        List<BizVendor> mine = bizVendorService.selectBizVendorList(query);
        if (mine.isEmpty())
        {
            return AjaxResult.error("未找到当前登录用户的商贩档案");
        }
        return AjaxResult.success(mine.get(0));
    }

    /**
     * 查询当前登录用户的摊贩审核状态
     * 返回状态信息：0=待审 1=通过 2=驳回 3=拉黑
     */
    @GetMapping("/me/status")
    public AjaxResult getMyVendorStatus()
    {
        Long wxUserId = MiniSecurityUtils.getWxUserId();
        BizVendor query = new BizVendor();
        query.setUserId(wxUserId);
        List<BizVendor> mine = bizVendorService.selectBizVendorList(query);
        if (mine.isEmpty())
        {
            return AjaxResult.success()
                .put("hasApplied", false)
                .put("status", null)
                .put("message", "未提交商贩档案申请");
        }

        BizVendor vendor = mine.get(0);
        AjaxResult result = AjaxResult.success();
        result.put("hasApplied", true);
        result.put("status", vendor.getStatus());
        result.put("vendorId", vendor.getVendorId());
        result.put("remark", vendor.getRemark());

        // 根据状态添加友好提示
        String statusStr = vendor.getStatus();
        if ("0".equals(statusStr))
        {
            result.put("message", "待审核中");
        }
        else if ("1".equals(statusStr))
        {
            result.put("message", "审核已通过");
        }
        else if ("2".equals(statusStr))
        {
            result.put("message", "审核已驳回");
        }
        else if ("3".equals(statusStr))
        {
            result.put("message", "已被拉黑");
        }

        return result;
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
        if (bizFeedback == null)
        {
            return AjaxResult.error("请求参数不能为空");
        }
        BizVendor vendor = bizVendorService.selectBizVendorByVendorId(vendorId);
        if (vendor == null)
        {
            return AjaxResult.error("商贩不存在");
        }
        if (bizFeedback.getRating() == null || bizFeedback.getRating() < 1 || bizFeedback.getRating() > 5)
        {
            return AjaxResult.error("评分必须在1到5之间");
        }
        if (StringUtils.isBlank(bizFeedback.getContent()))
        {
            return AjaxResult.error("评论内容不能为空");
        }
        if (bizFeedback.getContent().length() > 500)
        {
            return AjaxResult.error("评论内容不能超过500字");
        }

        Long wxUserId = MiniSecurityUtils.getWxUserId();
        bizFeedback.setFeedbackId(null);
        bizFeedback.setVendorId(vendorId);
        bizFeedback.setUserId(wxUserId);
        return toAjax(bizFeedbackService.insertBizFeedback(bizFeedback));
    }

    /**
     * 获取摊贩二维码基本信息（若不存在则自动生成商贩主页二维码）
     */
    @GetMapping("/{vendorId}/qrcode/basic")
    public AjaxResult getVendorQrcodeBasic(@PathVariable Long vendorId,
                                            @RequestParam(required = false, defaultValue = "1") Integer targetType)
    {
        BizVendor vendor = bizVendorService.selectBizVendorByVendorId(vendorId);
        if (vendor == null)
        {
            return AjaxResult.error("商贩不存在");
        }

        BizVendorQrcode qrcode = bizVendorQrcodeService.getOrCreateByVendorIdAndTargetType(vendorId, targetType);
        return AjaxResult.success()
            .put("shortId", qrcode.getShortId())
            .put("targetType", qrcode.getTargetType())
            .put("vendorId", qrcode.getVendorId())
            .put("status", qrcode.getStatus());
    }

    /**
     * 内部接口：生成摊贩二维码（幂等，不重复创建）
     */
    @PostMapping("/{vendorId}/qrcode/internal/generate")
    public AjaxResult generateVendorQrcodeInternal(@PathVariable Long vendorId,
                                                    @RequestParam(required = false, defaultValue = "1") Integer targetType)
    {
        BizVendor vendor = bizVendorService.selectBizVendorByVendorId(vendorId);
        if (vendor == null)
        {
            return AjaxResult.error("商贩不存在");
        }

        BizVendorQrcode before = bizVendorQrcodeService.selectByVendorIdAndTargetType(vendorId, targetType);
        BizVendorQrcode qrcode = bizVendorQrcodeService.getOrCreateByVendorIdAndTargetType(vendorId, targetType);
        return AjaxResult.success()
            .put("generated", before == null)
            .put("shortId", qrcode.getShortId())
            .put("targetType", qrcode.getTargetType())
            .put("vendorId", qrcode.getVendorId())
            .put("status", qrcode.getStatus());
    }
}

