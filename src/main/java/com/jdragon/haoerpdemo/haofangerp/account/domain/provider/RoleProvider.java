package com.jdragon.haoerpdemo.haofangerp.account.domain.provider;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午3:59
 * @Description: 角色sql构造类
 */
public class RoleProvider {

    /**
     * 根据用户名称获得角色列表
     * @param employeeNo
     * @return
     */
    public String roleByEmployeeNo(String employeeNo){
        return new SQL(){
            {
                SELECT_DISTINCT("r.role_name");
                FROM("system_employee m ");
                JOIN("system_employee_role mr on m.id=mr.employee_id","system_role r on r.id=mr.role_id");
                WHERE("m.employee_no = #{employeeNo}");
            }
        }.toString();
    }

    /**
     * 根据员工id获取已赋予角色列表
     * @param employeeId
     * @return
     */
    public String getAssignedRolesByEmployeeId(int employeeId){
        return new SQL(){
            {
                SELECT("r.*");
                FROM("system_role r");
                JOIN("system_employee_role mr on r.id=mr.role_id","system_employee m on m.id=mr.employee_id");
                WHERE("mr.employee_id = #{employeeId}");
            }
        }.toString();
    }


}
