package com.jdragon.haoerpdemo.haofangerp.production.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.20 11:22
 * @Description: 用户仓储层
 */
@Mapper
@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {
}
