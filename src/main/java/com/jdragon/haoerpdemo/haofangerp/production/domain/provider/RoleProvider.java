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
     * 根据用户名称获得角色列表
     * @param employeeNo
     * @return
     */
    public String roleByEmployeeNo(String employeeNo){
        return new SQL(){
            {
                SELECT_DISTINCT("r.role_name");
                FROM("system_member m ");
                JOIN("system_member_role mr on m.id=mr.member_id","system_role r on r.id=mr.role_id");
                WHERE("m.employee_no = #{employeeNo}");
            }
        }.toString();
    }
}
