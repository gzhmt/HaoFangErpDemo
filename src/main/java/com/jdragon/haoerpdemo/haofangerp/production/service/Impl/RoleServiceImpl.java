package com.jdragon.haoerpdemo.haofangerp.production.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Role;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.EmployeeRoleVo;
import com.jdragon.haoerpdemo.haofangerp.production.mappers.RoleMapper;
import com.jdragon.haoerpdemo.haofangerp.production.service.RoleService;
import com.jdragon.haoerpdemo.haofangerp.security.commons.SecurityContextHolderHelper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public IPage<Role> listRole(Page<Role> page){
        return baseMapper.selectPage(page,null);
    }

    @Override
    public List<EmployeeRoleVo> listEmployeeRole(int pageNo, int pageSize){
        PageHelper.startPage(pageNo, pageSize);
        return roleMapper.listEmployeeRole();
    }


    @Override
    public List<EmployeeRoleVo> getRolesByEmployeeNo(String employeeNo) {
        return roleMapper.getRolesByEmployeeNo(employeeNo);
    }

    @Override
    public boolean updateRoleOfEmployee(int employeeRoleId, int updateRoleId) {
        int rowCount = roleMapper.updateRoleOfEmployee(employeeRoleId, updateRoleId);
        return rowCount >= 1;
    }


}
