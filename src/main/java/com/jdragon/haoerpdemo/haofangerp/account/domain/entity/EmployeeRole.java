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

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午6:17
 * @Description
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("system_employee_role")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeRole extends Model<EmployeeRole> {

    /**
     * 唯一标识
     */
    @TableId(type = IdType.AUTO)
    private int id;

    /**
     * 角色id
     */
    private int roleId;

    /**
     * 工人id
     */
    private int employeeId;
}
