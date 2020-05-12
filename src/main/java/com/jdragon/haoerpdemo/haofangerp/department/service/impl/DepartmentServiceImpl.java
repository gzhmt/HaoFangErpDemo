package com.jdragon.haoerpdemo.haofangerp.department.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Employee;
import com.jdragon.haoerpdemo.haofangerp.account.mappers.EmployeeMapper;
import com.jdragon.haoerpdemo.haofangerp.account.service.EmployeeService;
import com.jdragon.haoerpdemo.haofangerp.commons.exceptions.HFException;
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
 * @Description: 部门相关服务
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
    /**
     * 先获取全部department信息，在递归操作中就不需要重复连接数据库，也避免了使用存储过程
     */
    private List<Department> departmentCache = new ArrayList<>();

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeMapper employeeMapper;
    /**
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

    /**
     * @Description: 获取我的下级部门
     **/
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
     * @params: [departmentId]
     * @return: boolean
     * @Description: 根据id删除部门
     **/
    @Override
    public boolean deleteDepartment(int departmentId) throws HFException {
        Department deletedDepartment = baseMapper.selectById(departmentId);
        if (deletedDepartment==null){
            throw new HFException("部门不存在");
        }
        List<Employee> departmentEmployees = employeeMapper
                .selectList(new LambdaQueryWrapper<Employee>()
                        .eq(Employee::getDepartmentId,departmentId));
        if(departmentEmployees.size()>0){
            throw new HFException("部门中还有员工，请先处理该部门的员工");
        }
        return deletedDepartment.deleteById();
    }
    /**
     * @params: [mergedDepartmentId:被合并的部门id, mergingDepartmentId:扩大的部门id]
     * @return: boolean
     * @Description: 合并部门 = 员工批量迁移 + 部门删除
     **/
    @Override
    public boolean mergeDepartment(int mergedDepartmentId, int mergingDepartmentId) throws HFException {
        Department mergedDepartment = baseMapper.selectById(mergedDepartmentId);
        Department mergingDepartment = baseMapper.selectById(mergingDepartmentId);
        if(mergedDepartment==null||mergingDepartment==null){
            throw new HFException("部门不存在");
        }
        baseMapper.mergeDepartment(mergedDepartmentId,mergingDepartmentId);
        return deleteDepartment(mergedDepartmentId);
    }

    /**
     * @Description: 获取我直系上级部门
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