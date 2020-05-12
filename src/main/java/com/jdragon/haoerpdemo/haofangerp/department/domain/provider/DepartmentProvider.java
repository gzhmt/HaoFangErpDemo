package com.jdragon.haoerpdemo.haofangerp.department.domain.provider;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.05.12 19:40
 * @Description:
 */
public class DepartmentProvider {
    public String mergeDepartment(int mergedDepartmentId,int mergingDepartmentId){
        return "UPDATE system_employee set department_id=#{mergingDepartmentId} where employee_no IN(" +
                " SELECT temp.employee_no from(" +
                " SELECT a.employee_no from system_employee a where a.department_id=#{mergedDepartmentId}" +
                ")" +
                " as temp)";
    }
}
