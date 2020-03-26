package com.jdragon.haoerpdemo.haofangerp.account.power.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午4:12
 * @Description: 权限实体类
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("system_power")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Power extends Model<Power> {
    @TableId(type = IdType.AUTO)
    private int id;

    private String apiName;

    private String apiUrl;

    private String apiMethod;

    private int apiSort;

    private int pid;

    private String describe;

    @Override
    protected Serializable pkVal(){
        return this.id;
    }
}
