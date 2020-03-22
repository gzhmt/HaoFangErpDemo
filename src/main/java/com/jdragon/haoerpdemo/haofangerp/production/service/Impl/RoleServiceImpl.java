package com.jdragon.haoerpdemo.haofangerp.production.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Role;
import com.jdragon.haoerpdemo.haofangerp.production.mappers.RoleMapper;
import com.jdragon.haoerpdemo.haofangerp.production.service.RoleService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.20 13:21
 * @Description: 用户角色服务接口实现类
 */
@CacheConfig(cacheNames = "role")
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Cacheable
    @Override
    public List<String> getRolesByEmployeeNo(String employeeNo) {
        return baseMapper.getRolesByEmployeeNo(employeeNo);
    }

    public boolean isAdmin(String employeeNo){
        return false;
    }
}
