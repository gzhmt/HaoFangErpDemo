package com.jdragon.haoerpdemo.haofangerp.account.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午4:29
 * @Description: 用户仓储层
 */
@Mapper
@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {
}

