package com.jdragon.haoerpdemo.haofangerp.commons.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.29 17:27
 * @Description:
 */
public enum  PlanAuditStatusEnum {
    待审核(1,"待审核"),
    审核通过(2,"审核通过"),
    被驳回(3,"被驳回");

    @EnumValue
    @Getter
    private int code;

    @Getter
    private String state;

    PlanAuditStatusEnum(int code, String state){
        this.code = code;
        this.state = state;
    }
}
