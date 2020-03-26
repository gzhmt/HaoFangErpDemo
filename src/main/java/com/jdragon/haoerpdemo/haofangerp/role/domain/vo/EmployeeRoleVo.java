package com.jdragon.haoerpdemo.haofangerp.role.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午3:34
 */
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeRoleVo {
    /**
     * 员工id
     */
    private int employeeId;
    /**
     * 工号
     */
    private String employeeNo;
    /**
     * 名字
     */
    private String name;

    /**
     * 员工角色id
     */
    private int employeeRoleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     *角色描述
     */
    private String roleDescribe;
}
