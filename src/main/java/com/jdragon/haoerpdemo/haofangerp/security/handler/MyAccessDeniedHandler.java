package com.jdragon.haoerpdemo.haofangerp.security.handler;

import com.baomidou.mybatisplus.extension.api.R;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.security.commons.JSONAuthentication;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.19 23:38
 * @Description: 权限校验处理器
 */
@Component
public class MyAccessDeniedHandler extends JSONAuthentication implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Result result = Result.PermissionsNotEnough("权限不足:"+accessDeniedException.getMessage());
        //输出
        this.writeJSON(httpServletRequest, httpServletResponse, result);
    }
}