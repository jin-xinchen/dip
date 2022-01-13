package com.itcheetah.securityjwt.security.handler;

import com.alibaba.fastjson.JSON;
import com.itcheetah.securityjwt.common.AjaxResult;
import com.itcheetah.securityjwt.common.HttpStatus;
import com.itcheetah.securityjwt.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义退出处理类 返回成功
 *
 * @author bee
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

//    @Autowired
//    private TokenService tokenService;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(HttpStatus.SUCCESS, "退出成功")));
    }
}
