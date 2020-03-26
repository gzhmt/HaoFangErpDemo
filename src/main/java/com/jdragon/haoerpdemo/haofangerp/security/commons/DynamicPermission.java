package com.jdragon.haoerpdemo.haofangerp.security.commons;

import com.jdragon.haoerpdemo.haofangerp.account.power.domain.entity.Power;
import com.jdragon.haoerpdemo.haofangerp.account.power.service.PowerService;
import com.jdragon.haoerpdemo.haofangerp.security.exception.MyAccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.19 23:27
 * @Description: 动态权限放行
 */
@Slf4j
@Component
public class DynamicPermission {

    @Autowired
    PowerService powerService;

    /**
     * 判断有访问API的权限
     *
     * @param request
     * @param authentication
     * @return
     * @throws MyAccessDeniedException
     */
    public boolean checkPermisstion(HttpServletRequest request,
                                    Authentication authentication)
            throws MyAccessDeniedException {
        Object principal = authentication.getPrincipal();
        log.debug("DynamicPermission principal = " + principal);

        if(principal instanceof UserDetails) {

            UserDetails userDetails = (UserDetails) principal;
            //得到当前的账号
            String username = userDetails.getUsername();

            log.debug(userDetails.getAuthorities().toString());
            log.debug("DynamicPermission  username = " + username);
            //通过账号获取资源鉴权
            List<Power> apiUrls = powerService.getPowerByEmployeeNo(username);

            AntPathMatcher antPathMatcher = new AntPathMatcher();
            //当前访问路径
            String requestURI = request.getRequestURI();
            //提交类型
            String urlMethod = request.getMethod();

            log.debug("DynamicPermission requestURI = " + requestURI);

            //判断当前路径中是否在资源鉴权中
            boolean rs = apiUrls.stream().anyMatch(item->{
                //判断URL是否匹配
                boolean hashAntPath = antPathMatcher.match(item.getApiUrl(),requestURI);

                //判断请求方式是否和数据库中匹配（数据库存储：GET,POST,PUT,DELETE）
                String dbMethod = item.getApiMethod();

                //处理null，万一数据库存值
                dbMethod = (dbMethod == null )? "": dbMethod;
                int hasMethod   = dbMethod.indexOf(urlMethod);

                log.debug("hashAntPath = " + hashAntPath);
                log.debug("hasMethod = " + hasMethod);
                log.debug("hashAntPath && hasMethod = " + (hashAntPath && hasMethod !=-1));
                //两者都成立，返回真，否则返回假
                return hashAntPath && (hasMethod !=-1);
            });
            //返回
            if (rs) {
                return rs;
            }else {
                throw  new MyAccessDeniedException("您没有访问该API的权限！");
            }

        }else{
            throw  new MyAccessDeniedException("不是UserDetails类型！");
        }
    }
}
