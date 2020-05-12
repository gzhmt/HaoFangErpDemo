package com.jdragon.haoerpdemo.haofangerp.department.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.annotations.Many;

import java.util.List;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.05.12 09:57
 * @Description:
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("system_department")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Department extends Model<Department> {

    private int id;

    private String name;

    private int pid;

    private int level;

    @TableField(exist = false)
    private List<Employee> employees;

    @TableField(exist = false)
    private List<Department> departments;

}