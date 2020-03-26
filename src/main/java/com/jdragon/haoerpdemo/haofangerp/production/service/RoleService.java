package com.jdragon.haoerpdemo.haofangerp.production.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Role;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.EmployeeRoleVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

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
     * 分页获取角色列表
     * @param page 页面
     * @return
     */
    IPage<Role> listRole(Page<Role> page);

    /**
     * 分页获取员工角色关系列表
     * @param pageNo 页数
     * @param pageSize 每一页的大小
     * @return
     */
    List<EmployeeRoleVo> listEmployeeRole(int pageNum, int pageSize);

    /**
     * 根据工号查询员工角色列表
     * @param employeeNo 工号
     * @return
     */
    List<EmployeeRoleVo> getRolesByEmployeeNo(String employeeNo);

    /**
     * 员工角色修改
     * @param employeeRoleId 员工角色id
     * @param updateRoleId 更新角色id
     * @return
     */
    boolean updateRoleOfEmployee(int employeeRoleId, int updateRoleId);
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
