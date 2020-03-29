package com.jdragon.haoerpdemo.haofangerp.account.domain.provider;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午4:12
 * @Description: 权限sql构造类
 */
public class PowerProvider {

    /**
     * 根据用户名称获得API URL资源鉴权
     * @param employeeNo
     * @return
     */
    public String powerByEmployeeNo(String employeeNo){
        return new SQL(){
            {
                SELECT_DISTINCT("p.*");
                FROM("system_employee m");
                JOIN("system_employee_role mb on m.id=mb.employee_id",
                        "system_role r on r.id=mb.role_id",
                        "system_role_power rp on r.id=rp.role_id",
                        "system_power p on p.id=rp.power_id");
                WHERE("m.employee_no=#{employeeNo}");
            }
        }.toString();
    }


    /**
     * 根据角色id获取已赋予权限列表
     * @param roleId 角色id
     * @return
     */
    public String getAssignedPowersByRoleId(int roleId){
        return new SQL(){
            {
                SELECT_DISTINCT("p.*");
                FROM("system_role r");
                JOIN("system_role_power rp on r.id=rp.role_id",
                        "system_power p on p.id=rp.power_id");
                WHERE("r.id=#{roleId}");
            }
        }.toString();
    }

}
