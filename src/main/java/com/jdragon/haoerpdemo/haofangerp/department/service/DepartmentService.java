package com.jdragon.haoerpdemo.haofangerp.department.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jdragon.haoerpdemo.haofangerp.commons.exceptions.HFException;
import com.jdragon.haoerpdemo.haofangerp.department.domain.Department;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.05.12 12:11
 * @Description:
 */
public interface DepartmentService extends IService<Department> {
    /**
     * 获取整个组织机构图
     */
    Department getAllOrganizational();

    /**
     * 获取我直系上级部门
     **/
    Department getSupOrganizational();

    /**
     * 获取我的下级部门
     **/
    Department getSubOrganizational();

    /**
     * 删除部门
     */
    boolean deleteDepartment(int departmentId) throws HFException;

    /**
     * 合并部门
     */
    boolean mergeDepartment(int mergedDepartmentId,int mergingDepartmentId) throws HFException;
}
