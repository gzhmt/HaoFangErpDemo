package com.jdragon.haoerpdemo.haofangerp.production.domain.provider;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.20 19:03
 * @Description: 角色sql构造类
 */
public class RoleProvider {

    /**
     * 获得所有员工角色列表
     * @return
     */
    public String listEmployeeRole(){
        return new SQL(){
            {
                SELECT_DISTINCT("m.id as employeeId","m.employee_no","m.name","mr.id as employeeRoleId","r.role_name","r.role_describe");
                FROM("system_employee m ");
                JOIN("system_employee_role mr on m.id=mr.employee_id","system_role r on r.id=mr.role_id");
            }
        }.toString();
    }

    /**
     * 根据工号查询员工角色列表
     * @param employeeNo
     * @return
     */
    public String getRolesByEmployeeNo(String employeeNo){
        return new SQL(){
            {
                SELECT_DISTINCT("m.id as employeeId","m.employee_no","m.name","mr.id as employeeRoleId","r.role_name","r.role_describe");
                FROM("system_employee m ");
                JOIN("system_employee_role mr on m.id=mr.employee_id","system_role r on r.id=mr.role_id");
                WHERE("m.employee_no = #{employeeNo}");
            }
        }.toString();
    }

    /**
     * 员工角色修改
     * @param employeeRoleId 员工角色id
     * @param updateRoleId 更新角色id
     * @return
     */
    public String updateRoleOfEmployee(int employeeRoleId, int updateRoleId){
        return new SQL(){
            {
                UPDATE("system_employee_role");
                SET("role_id = #{updateRoleId}");
                WHERE("id = #{employeeRoleId}");
            }
        }.toString();
    }
}
