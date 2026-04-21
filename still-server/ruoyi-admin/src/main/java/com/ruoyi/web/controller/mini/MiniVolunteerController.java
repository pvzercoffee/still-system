package com.ruoyi.web.controller.mini;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.MiniSecurityUtils;
import com.ruoyi.system.domain.BizVolunteer;
import com.ruoyi.system.domain.BizWxUser;
import com.ruoyi.system.service.IBizVolunteerService;
import com.ruoyi.system.service.IBizWxUserService;

/**
 * 小程序端志愿者接口
 */
@RestController
@RequestMapping("/mini/volunteers")
public class MiniVolunteerController extends BaseController
{
    @Autowired
    private IBizVolunteerService bizVolunteerService;

    @Autowired
    private IBizWxUserService bizWxUserService;

    /**
     * 申请为志愿者
     */
    @PostMapping("/apply")
    public AjaxResult apply(@RequestBody BizVolunteer bizVolunteer)
    {
        Long wxUserId = MiniSecurityUtils.getWxUserId();

        BizVolunteer query = new BizVolunteer();
        query.setUserId(wxUserId);
        List<BizVolunteer> mine = bizVolunteerService.selectBizVolunteerList(query);
        if (!mine.isEmpty())
        {
            return AjaxResult.error("已提交过志愿者申请");
        }

        bizVolunteer.setVolunteerId(null);
        bizVolunteer.setUserId(wxUserId);
        bizVolunteer.setStatus("0");
        int rows = bizVolunteerService.insertBizVolunteer(bizVolunteer);

        BizWxUser wxUser = bizWxUserService.selectBizWxUserByWxUserId(wxUserId);
        if (wxUser != null)
        {
            wxUser.setUserType("2");
            bizWxUserService.updateBizWxUser(wxUser);
        }
        return toAjax(rows);
    }
}

