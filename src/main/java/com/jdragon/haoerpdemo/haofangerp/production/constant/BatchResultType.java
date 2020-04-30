package com.jdragon.haoerpdemo.haofangerp.production.constant;

import lombok.Getter;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.04.30 23:51
 * @Description:
 */
public enum BatchResultType {
    编号("编号"),
    结果("结果"),
    消息("消息");
    @Getter
    private String des;

    BatchResultType(String des){
        this.des = des;
    }
}
