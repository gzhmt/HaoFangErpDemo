package com.jdragon.haoerpdemo.haofangerp.security.handler;

import com.baomidou.mybatisplus.extension.api.R;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.security.commons.JSONAuthentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.19 23:32
 * @Description: 登录失败接管处理
 */
@Component
public class MyAuthenticationFailureHandler  extends JSONAuthentication implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {
        Result result = Result.authFail().setResult("登录失败"+e.getMessage());
        //输出
        this.writeJSON(httpServletRequest, httpServletResponse, result);
    }
}
