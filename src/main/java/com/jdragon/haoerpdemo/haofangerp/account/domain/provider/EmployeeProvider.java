package com.jdragon.haoerpdemo.haofangerp.account.domain.provider;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/5/13 上午11:35
 * @Description
 */
public class EmployeeProvider {


    public String listEmployees(String keyWord){
        return new SQL(){
            {
                SELECT("id, employee_no, name");
                FROM("system_employee");
                if(keyWord != null){
                    WHERE("employee_no like #{keyword} or name like #{keyword}");
                }
            }
        }.toString();
    }
}
