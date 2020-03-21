package com.jdragon.haoerpdemo.haofangerp.production.domain.provider;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.20 19:05
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
                FROM("system_member m");
                JOIN("system_member_role mb on m.id=mb.member_id",
                        "system_role r on r.id=mb.role_id",
                        "system_role_power rp on r.id=rp.role_id",
                        "system_power p on p.id=rp.power_id");
                WHERE("m.employee_no=#{employeeNo}");
            }
        }.toString();
    }
}
