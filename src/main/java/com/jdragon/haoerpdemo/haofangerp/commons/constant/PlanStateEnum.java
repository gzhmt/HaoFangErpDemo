package com.jdragon.haoerpdemo.haofangerp.commons.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 18:44
 * @Description: 生产计划状态枚举
 */
public enum PlanStateEnum {
    新计划(1,"新计划"),
    审核通过(2,"审核通过"),
    生产中(3,"生产中"),
    已完成(4,"已完成"),
    被驳回(5,"被驳回");

    @EnumValue
    @Getter
    private int code;

    @Getter
    private String state;

    PlanStateEnum(int code, String state){
        this.code = code;
        this.state = state;
    }
    public static PlanStateEnum getEnumByState(String state) {
        return Arrays.asList(PlanStateEnum.values()).stream()
                .filter(planStateEnum -> planStateEnum.getState().equals(state))
                .findFirst().orElse(null);
    }
}
