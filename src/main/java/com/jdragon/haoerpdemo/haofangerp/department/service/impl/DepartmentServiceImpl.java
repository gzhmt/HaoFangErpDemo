package com.jdragon.haoerpdemo.haofangerp.department.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdragon.haoerpdemo.haofangerp.department.domain.Department;
import com.jdragon.haoerpdemo.haofangerp.department.mappers.DepartmentMapper;
import com.jdragon.haoerpdemo.haofangerp.department.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.05.12 12:12
 * @Description:
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
    @Override
    public Department getOrganizational() {
        Department rootDepartment =
                baseMapper.selectOne(new LambdaQueryWrapper<Department>().eq(Department::getLevel,0));
        if(!Optional.ofNullable(rootDepartment).isPresent()){
            return null;
        }
        this.recursiveDepartment(rootDepartment);
        System.out.println(rootDepartment.toString());
        return rootDepartment;
    }
    public void recursiveDepartment(Department parentDepartment){
        List<Department> departments = baseMapper
                .selectList(new LambdaQueryWrapper<Department>()
                        .eq(Department::getPid,parentDepartment.getId()));
        parentDepartment.setDepartments(departments);
        for(Department department:departments){
            recursiveDepartment(department);
        }
    }
}
