package com.jdragon.haoerpdemo.haofangerp.commons.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 18:53
 * @Description: 生产计划状况枚举
 */
public enum PlanStatusEnum {

    顺利(1,"顺利"),
    延后(2,"延后"),
    暂停(3,"暂停"),
    中止(4,"中止");

    @EnumValue
    private int code;

    private String status;

    PlanStatusEnum(int code, String status){
        this.code = code;
        this.status = status;
    }
}
