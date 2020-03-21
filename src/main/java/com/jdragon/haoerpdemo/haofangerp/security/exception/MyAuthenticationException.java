package com.jdragon.haoerpdemo.haofangerp.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.19 23:42
 * @Description: 异常类
 */
public class MyAuthenticationException  extends AuthenticationException {

    public MyAuthenticationException(String msg) {
        super(msg);
    }
}