package com.jdragon.haoerpdemo.haofangerp.production.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Role;
import com.jdragon.haoerpdemo.haofangerp.production.domain.provider.RoleProvider;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.EmployeeRoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.20 11:22
 * @Description: 用户角色仓储层
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
