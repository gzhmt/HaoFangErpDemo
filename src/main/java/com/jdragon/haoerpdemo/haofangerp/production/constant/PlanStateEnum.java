package com.jdragon.haoerpdemo.haofangerp.production.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
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
    生产中(2,"生产中"),
    已完成(3,"已完成");


    @EnumValue
    @Getter
    private int code;

    @Getter
    private String state;

    PlanStateEnum(int code, String state){
        this.code = code;
        this.state = state;
    }

    /**
     * @author 金柏宇
     */
    public int getCode() {
        return code;
    }

    /**
     * 根据状态码获得对应枚举
     * @author 金柏宇
     * @param code 计划状态码
     * @return 状态码对应的枚举
     */
    public static PlanStateEnum getPlanStateEnumByCode(int code){
        for(PlanStateEnum planStateEnum:PlanStateEnum.values()){
            if(planStateEnum.getCode()==code){
                return planStateEnum;
            }
        }
        return null;
    }
    public static PlanStateEnum getEnumByState(String state) {
        return Arrays.asList(PlanStateEnum.values()).stream()
                .filter(planStateEnum -> planStateEnum.getState().equals(state))
                .findFirst().orElse(null);
    }
}
