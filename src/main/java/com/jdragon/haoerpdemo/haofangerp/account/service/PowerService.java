package com.jdragon.haoerpdemo.haofangerp.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Power;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Role;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;

import java.util.List;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午4:17
 * @Description: 用户权限服务接口类
 */
public interface PowerService extends IService<Power> {

    List<Power> getPowerByEmployeeNo(String employeeNo);

    /**
     * 获取权限列表(按权重从大到小排列)
     * @return
     */
    List<Power> listPowers();

    /**
     * 根据角色id分页获取已赋予权限列表
     * @param pageNo 页数
     * @param pageSize 每一页的大小
     * @param roleId 角色id
     * @return
     */
    List<Power> getAssignedPowersByRoleId(int pageNo, int pageSize, int roleId);

    /**
     * 根据角色id分页获取未赋予权限列表
     * @param pageNo 页数
     * @param pageSize 每一页的大小
     * @param roleId 角色id
     * @return
     */
    List<Power> getUnAssignedPowersByRoleId(int pageNo, int pageSize, int roleId);

    /**
     * 添加角色权限
     * @param roleId 角色id
     * @param powerId 权限id
     * @return
     */
    Result addPowerOfRole(int roleId, int powerId);

    /**
     * 删除角色权限
     * @param roleId 角色id
     * @param powerId 权限id
     * @return
     */
    Result deletePowerOfRole(int roleId, int powerId);
}
