package com.jdragon.haoerpdemo.haofangerp.production.constant;

import lombok.Getter;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.04.30 23:58
 * @Description:
 */
public enum  BatchResult {
    成功("成功"),
    失败("失败");

    @Getter
    private String des;

    BatchResult(String des){
        this.des = des;
    }
}
