package com.jdragon.haoerpdemo.haofangerp.department.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Employee;
import com.jdragon.haoerpdemo.haofangerp.account.mappers.EmployeeMapper;
import com.jdragon.haoerpdemo.haofangerp.account.service.EmployeeService;
import com.jdragon.haoerpdemo.haofangerp.department.domain.Department;
import com.jdragon.haoerpdemo.haofangerp.department.mappers.DepartmentMapper;
import com.jdragon.haoerpdemo.haofangerp.department.service.DepartmentService;
import com.jdragon.haoerpdemo.haofangerp.security.commons.SecurityContextHolderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.05.12 12:12
 * @Description:
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    List<Department> departmentCache = new ArrayList<>();

    @Autowired
    EmployeeService employeeService;

    /**
     * @params: []
     * @return: com.jdragon.haoerpdemo.haofangerp.department.domain.Department
     * @Description: 获取整个组织结构图
     **/
    @Override
    public Department getAllOrganizational() {
        departmentCache = baseMapper.selectList(null);
        //先获取根级
        Optional<Department> rootDepartment = departmentCache.stream()
                .filter(department -> department.getPid() == 0)
                .findFirst();

        return rootDepartment.map(this::recursiveSubDepartment).orElse(null);
    }



    @Override
    public Department getSubOrganizational(){
        departmentCache = baseMapper.selectList(null);
        Employee employee = employeeService.getEmployeeByEmployeeNo(SecurityContextHolderHelper.getEmployeeNo());
        //先获取我的下级部门
        Optional<Department> sonDepartment = departmentCache.stream()
                .filter(department -> department.getPid() == employee.getDepartmentId())
                .findFirst();

        return sonDepartment.map(this::recursiveSubDepartment).orElse(null);
    }

    /**
     * @params: []
     * @return: com.jdragon.haoerpdemo.haofangerp.department.domain.Department
     * @Description: 获取我的所有直系上级
     **/
    @Override
    public Department getSupOrganizational() {
        departmentCache = baseMapper.selectList(null);
        Employee employee = employeeService.getEmployeeByEmployeeNo(SecurityContextHolderHelper.getEmployeeNo());
        //先获取我的上级部门
        Department mineDepartment = baseMapper.selectById(employee.getDepartmentId());
        Optional<Department> parentDepartmentOptional = departmentCache.stream()
                .filter(department -> department.getId() == mineDepartment.getPid())
                .findFirst();

        return parentDepartmentOptional.map(this::recursiveSupDepartment).orElse(null);
    }

    /**
     * @params: [parentDepartment]
     * @return: com.jdragon.haoerpdemo.haofangerp.department.domain.Department
     * @Description: 获取有关上级的组织机构图递归方法
     **/
    public Department recursiveSupDepartment(Department sonDepartment) {
        if (sonDepartment.getPid() == 0) {
            return sonDepartment;
        }
        Optional<Department> parentDepartmentOptional = departmentCache.stream()
                .filter(department -> department.getId() == sonDepartment.getPid())
                .findFirst();

        if (parentDepartmentOptional.isPresent()) {
            Department parentDepartment = parentDepartmentOptional.get();
            List<Department> departments = new ArrayList<>();
            departments.add(sonDepartment);
            parentDepartment.setDepartments(departments);
            return recursiveSupDepartment(parentDepartment);
        }
        return null;
    }

    /**
     * @params: [parentDepartment]
     * @return: com.jdragon.haoerpdemo.haofangerp.department.domain.Department
     * @Description: 获取向下级的组织机构图递归方法
     **/
    public Department recursiveSubDepartment(Department parentDepartment) {
        List<Department> departments = departmentCache.stream()
                .filter(department -> department.getPid() == parentDepartment.getId())
                .collect(Collectors.toList());

        parentDepartment.setDepartments(departments);

        for (Department department : departments) {
            recursiveSubDepartment(department);
        }
        return parentDepartment;
    }
}