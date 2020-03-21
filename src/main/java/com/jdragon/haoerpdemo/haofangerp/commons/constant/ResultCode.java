package com.jdragon.haoerpdemo.haofangerp.commons.constant;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 17:47
 * @Description: 返回结果枚举
 */
public enum ResultCode {

    NORMAL(20000l, "正常响应"),
    AUTH_FAIL(20001l, "认证失败"),
    SYSTEM_ERROR(20002l, "系统异常"),
    PARAMS_ERROR(20003l,"参数非法");

    @Setter
    @Getter
    private Long code;

    @Setter
    @Getter
    private String message;

    ResultCode(Long code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }
}
