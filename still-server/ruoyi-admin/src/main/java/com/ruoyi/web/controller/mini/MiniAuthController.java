package com.ruoyi.web.controller.mini;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.MiniLoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.framework.web.service.MiniTokenService;
import com.ruoyi.system.domain.BizWxUser;
import com.ruoyi.system.service.IBizWxUserService;
import com.ruoyi.common.utils.StringUtils;

/**
 * 小程序端认证接口
 */
@RestController
@RequestMapping("/mini/auth")
public class MiniAuthController
{
    @Autowired
    private IBizWxUserService bizWxUserService;

    @Autowired
    private MiniTokenService miniTokenService;

    @Anonymous
    @PostMapping("/register")
    public AjaxResult register(@RequestBody MiniAuthBody body)
    {
        String openid = resolveOpenid(body.getCode(), body.getOpenid());
        BizWxUser wxUser = bizWxUserService.selectBizWxUserByOpenid(openid);
        if (wxUser == null)
        {
            wxUser = new BizWxUser();
            wxUser.setOpenid(openid);
            wxUser.setUnionid(StringUtils.defaultString(body.getUnionid()));
            wxUser.setNickname(StringUtils.isEmpty(body.getNickname()) ? "微信用户" : body.getNickname());
            wxUser.setAvatarUrl(StringUtils.defaultString(body.getAvatarUrl()));
            wxUser.setPhone(StringUtils.defaultString(body.getPhone()));
            wxUser.setUserType("0");
            wxUser.setStatus("0");
            bizWxUserService.insertBizWxUser(wxUser);
        }
        else
        {
            // 注册接口允许补充资料
            wxUser.setUnionid(StringUtils.defaultString(body.getUnionid(), wxUser.getUnionid()));
            wxUser.setNickname(StringUtils.defaultString(body.getNickname(), wxUser.getNickname()));
            wxUser.setAvatarUrl(StringUtils.defaultString(body.getAvatarUrl(), wxUser.getAvatarUrl()));
            wxUser.setPhone(StringUtils.defaultString(body.getPhone(), wxUser.getPhone()));
            wxUser.setLastLoginTime(new Date());
            bizWxUserService.updateBizWxUser(wxUser);
        }
        return buildLoginResult(wxUser);
    }

    @Anonymous
    @PostMapping("/login")
    public AjaxResult login(@RequestBody MiniAuthBody body)
    {
        String openid = resolveOpenid(body.getCode(), body.getOpenid());
        BizWxUser wxUser = bizWxUserService.selectBizWxUserByOpenid(openid);
        if (wxUser == null)
        {
            // 微信端常见无感注册：首次登录自动创建用户
            wxUser = new BizWxUser();
            wxUser.setOpenid(openid);
            wxUser.setNickname(StringUtils.isEmpty(body.getNickname()) ? "微信用户" : body.getNickname());
            wxUser.setAvatarUrl(StringUtils.defaultString(body.getAvatarUrl()));
            wxUser.setPhone(StringUtils.defaultString(body.getPhone()));
            wxUser.setUnionid(StringUtils.defaultString(body.getUnionid()));
            wxUser.setUserType("0");
            wxUser.setStatus("0");
            bizWxUserService.insertBizWxUser(wxUser);
        }
        wxUser.setLastLoginTime(new Date());
        bizWxUserService.updateBizWxUser(wxUser);
        return buildLoginResult(wxUser);
    }

    private AjaxResult buildLoginResult(BizWxUser wxUser)
    {
        if ("1".equals(wxUser.getStatus()))
        {
            return AjaxResult.error("账号已封禁");
        }
        MiniLoginUser loginUser = new MiniLoginUser();
        loginUser.setWxUserId(wxUser.getWxUserId());
        loginUser.setOpenid(wxUser.getOpenid());
        loginUser.setUserType(wxUser.getUserType());
        loginUser.setStatus(wxUser.getStatus());
        String token = miniTokenService.createToken(loginUser);

        AjaxResult ajax = AjaxResult.success();
        ajax.put(Constants.TOKEN, token);
        ajax.put("user", wxUser);
        return ajax;
    }

    private String resolveOpenid(String code, String openid)
    {
        if (StringUtils.isNotEmpty(openid))
        {
            return openid;
        }
        if (StringUtils.isEmpty(code))
        {
            throw new ServiceException("code/openid不能同时为空", HttpStatus.BAD_REQUEST);
        }
        return "wx_" + DigestUtils.md5DigestAsHex(code.getBytes(StandardCharsets.UTF_8));
    }

    public static class MiniAuthBody
    {
        private String code;
        private String openid;
        private String unionid;
        private String nickname;
        private String avatarUrl;
        private String phone;

        public String getCode()
        {
            return code;
        }

        public void setCode(String code)
        {
            this.code = code;
        }

        public String getOpenid()
        {
            return openid;
        }

        public void setOpenid(String openid)
        {
            this.openid = openid;
        }

        public String getUnionid()
        {
            return unionid;
        }

        public void setUnionid(String unionid)
        {
            this.unionid = unionid;
        }

        public String getNickname()
        {
            return nickname;
        }

        public void setNickname(String nickname)
        {
            this.nickname = nickname;
        }

        public String getAvatarUrl()
        {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl)
        {
            this.avatarUrl = avatarUrl;
        }

        public String getPhone()
        {
            return phone;
        }

        public void setPhone(String phone)
        {
            this.phone = phone;
        }
    }
}
