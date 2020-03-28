package com.jdragon.haoerpdemo.haofangerp.account.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.EmployeeRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/27 上午10:17
 * @Description
 */
@Mapper
@Repository
public interface EmployeeRoleMapper extends BaseMapper<EmployeeRole> {

    @Select("select * from system_employee_role where employee_id = #{employeeId} and role_id = #{roleId}")
    EmployeeRole getEmployRole(int employeeId, int roleId);

    @Delete("delete from system_employee_role where employee_id = #{employeeId} and role_id = #{roleId}")
    int deleteEmployRole(int employeeId, int roleId);
}
