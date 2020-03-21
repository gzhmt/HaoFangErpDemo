package com.jdragon.haoerpdemo.haofangerp.production.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.20 13:20
 * @Description: 用户角色服务接口类
 */
public interface RoleService extends IService<Role> {
    /**
     * 根据用户名称查询角色
     * @param employeeNo
     * @return
     */
    List<String> getRolesByEmployeeNo(String employeeNo);
//
//    /**
//     * 根据roleId找用户以及用户是否被设置在某个角色上，用在显示在用于角色设置处
//     * @param roleId
//     * @return
//     */
//    List<SysRoleAndPermissionVo> getRoleAndUserList(String roleId);
//
//    /**
//     * 根据roleId找菜单
//     */
//    List<SysRoleAndPermissionVo> getRoleAndMenuList(String roleId) throws SQLIntegrityConstraintViolationException, Exception;
//
//    /**
//     * 根据roleId找API
//     */
//    List<SysRoleAndPermissionVo> getRoleAndApiList(String roleId);

}
