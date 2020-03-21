package com.jdragon.haoerpdemo.haofangerp.production.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Role;
import com.jdragon.haoerpdemo.haofangerp.production.domain.provider.RoleProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.20 11:22
 * @Description: 用户角色仓储层
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    @SelectProvider(type = RoleProvider.class,method = "roleByEmployeeNo")
    List<String> getRolesByEmployeeNo(String employeeNo);
}
