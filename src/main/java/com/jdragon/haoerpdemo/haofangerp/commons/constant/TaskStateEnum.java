package com.jdragon.haoerpdemo.haofangerp.commons.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 19:12
 * @Description: 任务状态枚举
 */
public enum  TaskStateEnum {
    正常(1,"正常"),
    生产中(2,"生产中"),
    已完成(3,"已完成"),
    暂停(4,"暂停"),
    撤销(5,"已撤销");

    @EnumValue
    private int code;

    private String status;

    TaskStateEnum(int code,String status){
        this.code = code;
        this.status = status;
    }
}
