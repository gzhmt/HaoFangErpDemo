package com.jdragon.haoerpdemo.haofangerp.security.service;

import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Member;
import com.jdragon.haoerpdemo.haofangerp.production.service.MemberService;
import com.jdragon.haoerpdemo.haofangerp.production.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.19 23:23
 * @Description: 使UserDetail与数据库交互
 */
@Slf4j
@Service
public class AuthUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private MemberService memberService;

    @Autowired
    private RoleService roleService;
    /**
     * 通过账号查找用户、角色的信息
     * @param employeeNo
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String employeeNo) throws UsernameNotFoundException {
        Member user = memberService.getMemberByEmployeeNo(employeeNo);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("%s.这个用户不存在", employeeNo));
        }else {
            //查找角色
            List<String> roles =  roleService.getRolesByEmployeeNo(employeeNo);
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
            log.debug("loadUserByEmployeeN0.....user ===> " + user);
            return new AuthUserEntity(user.getEmployeeNo(), user.getPassword(), authorities);
        }
    }
}
