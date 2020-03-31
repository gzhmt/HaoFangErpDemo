package com.jdragon.haoerpdemo.haofangerp.account.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Role;
import com.jdragon.haoerpdemo.haofangerp.account.domain.vo.RoleVo;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;

import java.util.List;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午3:55
 * @Description: 用户角色服务接口类
 */
public interface RoleService {

    /**
     * 根据用户名称查询角色
     * @param employeeNo
     * @return
     */
    List<String> getRolesByEmployeeNo(String employeeNo);


    /**
     * 分页获取角色列表
     * @param page 页面
     * @return
     */
    IPage<Role> listRoles(Page<Role> page);


    /**
     * 添加角色
     * @param roleVo 角色vo类
     * @return
     */
    Role addRole(RoleVo roleVo) throws Exception;

    /**
     * 根据角色id删除角色
     * @param roleId 角色id
     * @return
     */
    boolean deleteRole(int roleId) throws Exception;

    /**
     * 根据员工id分页获取已赋予角色列表
     * @param pageNo 页数
     * @param pageSize 每一页的大小
     * @param employeeId 员工id
     * @return
     */
    List<Role> getAssignedRolesByEmployeeId(int pageNo, int pageSize, int employeeId);


    /**
     * 根据员工id分页获取未赋予角色列表
     * @param pageNo 页数
     * @param pageSize 每一页的大小
     * @param employeeId 员工id
     * @return
     */
    List<Role> getUnAssignedRolesByEmployeeId(int pageNo, int pageSize, int employeeId);


    /**
     * 添加员工角色
     * @param employeeId 员工id
     * @param roleId 角色id
     * @return
     */
    boolean addRoleOfEmployee(int employeeId, int roleId) throws Exception;

    /**
     * 删除员工角色
     * @param employeeId 员工id
     * @param roleId 角色id
     * @return
     */
    boolean deleteRoleOfEmployee(int employeeId, int roleId) throws Exception;
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
