package com.jdragon.haoerpdemo.haofangerp.security.handler;

import com.jdragon.haoerpdemo.haofangerp.security.commons.JSONAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.19 23:39
 * @Description: 退出接管处理
 */
@Slf4j
@Component
public class MyLogoutHandler extends JSONAuthentication implements LogoutHandler {

    @Value("${jwt.header}")
    private String header;

    @Override
    public void logout(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       Authentication authentication) {
        String headerToken = httpServletRequest.getHeader(header);
        log.debug("logout header Token = " + headerToken);
        log.debug("logout request getMethod = " + httpServletRequest.getMethod());
        //
        if (!StringUtils.isEmpty(headerToken)) {
            //postMan测试时，自动假如的前缀，要去掉。
            String token = headerToken.replace("Bearer", "").trim();
            log.debug("authentication = " + authentication);
            SecurityContextHolder.clearContext();
        }
    }
}