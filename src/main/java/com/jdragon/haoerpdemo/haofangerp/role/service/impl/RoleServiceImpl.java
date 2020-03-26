package com.jdragon.haoerpdemo.haofangerp.role.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.jdragon.haoerpdemo.haofangerp.role.domain.entity.Role;
import com.jdragon.haoerpdemo.haofangerp.role.domain.vo.EmployeeRoleVo;
import com.jdragon.haoerpdemo.haofangerp.role.mappers.RoleMapper;
import com.jdragon.haoerpdemo.haofangerp.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午3:37
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
