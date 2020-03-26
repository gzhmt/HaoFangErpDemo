package com.jdragon.haoerpdemo.haofangerp.role.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.haoerpdemo.haofangerp.role.domain.entity.Role;
import com.jdragon.haoerpdemo.haofangerp.role.domain.provider.RoleProvider;
import com.jdragon.haoerpdemo.haofangerp.role.domain.vo.EmployeeRoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午3:35
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 分页获取员工角色关系列表
     * @return
     */
    @SelectProvider(type = RoleProvider.class,method = "listEmployeeRole")
    List<EmployeeRoleVo> listEmployeeRole();

    /**
     * 根据工号查询角色列表
     * @param employeeNo
     * @return
     */
    @SelectProvider(type = RoleProvider.class,method = "getRolesByEmployeeNo")
    List<EmployeeRoleVo> getRolesByEmployeeNo(String employeeNo);


    /**
     * 员工角色修改
     * @param employeeRoleId 员工角色id
     * @param updateRoleId 更新角色id
     * @return
     */
    @UpdateProvider(type = RoleProvider.class,method = "updateRoleOfEmployee")
    int updateRoleOfEmployee(int employeeRoleId, int updateRoleId);
}
