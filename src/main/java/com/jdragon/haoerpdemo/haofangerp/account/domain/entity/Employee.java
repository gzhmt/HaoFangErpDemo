package com.jdragon.haoerpdemo.haofangerp.account.domain.entity;

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
import java.util.Date;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午4:32
 * @Description: 用户表实体类
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("system_employee")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee extends Model<Employee> {

    @TableId(type = IdType.AUTO)
    private int id;

    private String employeeNo;

    private String password;

    private String name;

    private Date birth;

    private Date entryTime;

    @Override
    protected Serializable pkVal(){
        return this.id;
    }
}
