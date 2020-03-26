package com.jdragon.haoerpdemo.haofangerp.security.handler;

import com.jdragon.haoerpdemo.haofangerp.account.service.PowerService;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.security.commons.JSONAuthentication;
import com.jdragon.haoerpdemo.haofangerp.security.commons.TokenCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.19 23:32
 * @Description: 登录成功接管处理
 */
@Slf4j
@Component
public class MyAuthenticationSuccessHandler extends JSONAuthentication implements AuthenticationSuccessHandler {
    @Autowired
    PowerService powerService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("userDetails = " + userDetails.toString());

        //取token
        //好的解决方案，登录成功后token存储到数据库中
        //只要token还在过期内，不需要每次重新生成
        //先去缓存中找
        String token = TokenCache.getTokenFromCache(userDetails.getUsername());

        if(token ==null) {
            log.debug(userDetails.getUsername()+"初次登录，token还没有，生成新token。。。。。。");
            //如果token为空，则去创建一个新的token
            token = jwtService.generateToken(userDetails);

            //把新的token存储到缓存中
            TokenCache.setToken(userDetails.getUsername(),token);
        }

        Map<String,Object> map = new HashMap<>();
        map.put("username",userDetails.getUsername());
        map.put("auth",userDetails.getAuthorities());
        map.put("token",token);
        map.put("power",powerService.getPowerByEmployeeNo(userDetails.getUsername()));

        Result result = Result.success("登录成功").setResult(map);
        this.writeJSON(request,response,result);
    }
}
