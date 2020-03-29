package com.jdragon.haoerpdemo.haofangerp.security.commons;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.22 20:07
 * @Description: SecurityContextHolder辅助类
 */
public class SecurityContextHolderHelper {
    /**
     * @return 认证员工工号
     */
    public static String getEmployeeNo(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    /**
     * @return 权限列表
     */
    public static List<String> getAuthorities(){
        List<SimpleGrantedAuthority> simpleGrantedAuthority=
                (List<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        List<String> authorities = new ArrayList<>();
        simpleGrantedAuthority.forEach(e->authorities.add(e.getAuthority()));
        return authorities;
    }

    /**
     * 对比登录工号和传入工号是否相同
     */
    public static boolean isAuthorities(String employeeNo){
        String authority = SecurityContextHolderHelper.getEmployeeNo();
        return employeeNo.equals(authority);
    }
}
