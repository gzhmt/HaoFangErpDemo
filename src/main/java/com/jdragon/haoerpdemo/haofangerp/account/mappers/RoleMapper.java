package com.jdragon.haoerpdemo.haofangerp.account.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Role;
import com.jdragon.haoerpdemo.haofangerp.account.domain.provider.RoleProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午3:54
 * @Description: 用户角色仓储层
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    @SelectProvider(type = RoleProvider.class,method = "roleByEmployeeNo")
    List<String> getRolesByEmployeeNo(String employeeNo);


    /**
     * 获取角色列表(按权重从大到小排列)
     * @return
     */
    @Select("select * from system_role r order by r.role_sort desc")
    List<Role> listRoles();


    /**
     * 根据角色名称获取角色信息
     * @param roleName
     * @return
     */
    @Select("select * from system_role where role_name = #{roleName}")
    Role getRoleByRoleName(String roleName);

    /**
     * 根据角色id查询此角色被员工依赖次数
     * @param roleId 角色id
     * @return
     */
    @Select("select count(*) from system_employee_role mr where mr.role_id = #{roleId}")
    int getRoleCountByRoleId(int roleId);

    /**
     * 根据员工id获取已赋予角色列表
     * @param EmployeeId 员工id
     * @return
     */
    @SelectProvider(type = RoleProvider.class,method = "getAssignedRolesByEmployeeId")
    List<Role> getAssignedRolesByEmployeeId(int employeeId);

    /**
     * 根据员工id获取未赋予角色列表
     * @param EmployeeId 员工id
     * @return
     */
    @Select("select * from system_role r where r.id not in" +
            " (select r.id from system_role r" +
            " join system_employee_role mr on r.id=mr.role_id" +
            " join system_employee m on m.id=mr.employee_id" +
            " where mr.employee_id = #{employeeId})" +
            " order by r.role_sort desc")
    List<Role> getUnAssignedRolesByEmployeeId(int employeeId);

}
