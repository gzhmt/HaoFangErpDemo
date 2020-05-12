package com.jdragon.haoerpdemo.haofangerp.department.mappers;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.haoerpdemo.haofangerp.department.domain.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.05.12 09:59
 * @Description:
 */
@Mapper
@Repository
public interface DepartmentMapper extends BaseMapper<Department> {
}
