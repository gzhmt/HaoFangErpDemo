package com.jdragon.haoerpdemo.haofangerp.security.exception;

import org.springframework.security.access.AccessDeniedException;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.19 23:41
 * @Description: 异常类
 */
public class MyAccessDeniedException extends AccessDeniedException {
    public MyAccessDeniedException(String msg) {
        super(msg);
    }
}
