package com.jdragon.haoerpdemo.haofangerp.commons.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 19:16
 * @Description: 紧急程度的枚举
 */
public enum EmergencyLevelEnum {

    正常(1,"正常"),
    加急(2,"加急"),
    特急(3,"特急");

    @EnumValue
    private int code;

    private String state;

    EmergencyLevelEnum(int code, String state){
        this.code = code;
        this.state = state;
    }
}
