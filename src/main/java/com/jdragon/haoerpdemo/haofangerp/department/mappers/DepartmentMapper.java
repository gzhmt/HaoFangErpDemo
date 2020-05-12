package com.jdragon.haoerpdemo.haofangerp.department.mappers;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.haoerpdemo.haofangerp.department.domain.Department;
import com.jdragon.haoerpdemo.haofangerp.department.domain.provider.DepartmentProvider;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.05.12 09:59
 * @Description:
 */
@Mapper
@Repository
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * @param mergedDepartmentId
     * @param mergingDepartmentId
     * @return 将被合并部门中的员工departmentId改为扩大部门的id
     */
    @UpdateProvider(type = DepartmentProvider.class,method = "mergeDepartment")
    int mergeDepartment(int mergedDepartmentId,int mergingDepartmentId);
}
