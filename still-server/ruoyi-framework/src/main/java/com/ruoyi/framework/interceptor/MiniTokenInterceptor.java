package com.ruoyi.framework.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.MiniLoginUser;
import com.ruoyi.common.utils.MiniSecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.web.service.MiniTokenService;

/**
 * 小程序端令牌拦截器
 */
@Component
public class MiniTokenInterceptor implements HandlerInterceptor
{
    @Autowired
    private MiniTokenService miniTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        MiniLoginUser loginUser = miniTokenService.getLoginUser(request);
        if (loginUser == null)
        {
            ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(401, "小程序端token无效或已过期")));
            return false;
        }
        miniTokenService.verifyToken(loginUser);
        MiniSecurityUtils.setLoginUser(loginUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
    {
        MiniSecurityUtils.clear();
    }
}

