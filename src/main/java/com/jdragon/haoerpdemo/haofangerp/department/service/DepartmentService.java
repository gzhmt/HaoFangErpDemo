package com.jdragon.haoerpdemo.haofangerp.department.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jdragon.haoerpdemo.haofangerp.department.domain.Department;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.05.12 12:11
 * @Description:
 */
public interface DepartmentService extends IService<Department> {
    Department getOrganizational();
}
