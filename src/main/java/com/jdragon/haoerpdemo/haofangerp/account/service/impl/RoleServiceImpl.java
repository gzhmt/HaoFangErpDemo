package com.jdragon.haoerpdemo.haofangerp.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Employee;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.EmployeeRole;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Role;
import com.jdragon.haoerpdemo.haofangerp.account.domain.vo.RoleVo;
import com.jdragon.haoerpdemo.haofangerp.account.mappers.EmployeeRoleMapper;
import com.jdragon.haoerpdemo.haofangerp.account.mappers.RoleMapper;
import com.jdragon.haoerpdemo.haofangerp.account.service.RoleService;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午3:55
 * @Description: 用户角色服务接口实现类
 */
@CacheConfig(cacheNames = "role")
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private EmployeeRoleMapper employeeRoleMapper;

    @Override
    public List<String> getRolesByEmployeeNo(String employeeNo) {
        return baseMapper.getRolesByEmployeeNo(employeeNo);
    }

    @Override
    public List<Role> listRoles() {
        return roleMapper.listRoles();
    }

    @Override
    public Result addRole(RoleVo roleVo) {
        String prefix = "ROLE_";
        String roleName = prefix + roleVo.getRoleName().toUpperCase();
        if(!Optional.ofNullable(roleMapper.getRoleByRoleName(roleName)).isPresent()) {
            return Result.error("该角色已存在");
        }
        Role role = new Role();
        role.setRoleName(roleName);
        role.setRoleSort(roleVo.getRoleSort());
        role.setRoleDescribe(roleVo.getRoleDescribe());
        roleMapper.insert(role);
        return Result.success("增加角色成功");
    }

    @Override
    public Result deleteRole(int roleId) {
        if(roleMapper.getRoleCountByRoleId(roleId) == 0){
            roleMapper.deleteById(roleId);
            return Result.success("删除角色成功");
        }
        return Result.error("该角色被员工依赖,无法删除");
    }

    @Override
    public List<Role> getAssignedRolesByEmployeeId(int pageNo, int pageSize, int employeeId){
        PageHelper.startPage(pageNo, pageSize);
        return roleMapper.getAssignedRolesByEmployeeId(employeeId);
    }

    @Override
    public List<Role> getUnAssignedRolesByEmployeeId(int pageNo, int pageSize, int employeeId) {
        PageHelper.startPage(pageNo, pageSize);
        return roleMapper.getUnAssignedRolesByEmployeeId(employeeId);
    }

    @Override
    public Result addRoleOfEmployee(int employeeId, int roleId) {
        if(!Optional.ofNullable(employeeRoleMapper.getEmployeeRole(employeeId, roleId)).isPresent()){
            EmployeeRole employeeRole = new EmployeeRole();
            employeeRole.setEmployeeId(employeeId);
            employeeRole.setRoleId(roleId);
            employeeRoleMapper.insert(employeeRole);
            return Result.success("添加员工角色成功");
        }
        return Result.error("该员工角色已存在");
    }

    @Override
    public Result deleteRoleOfEmployee(int employeeId, int roleId) {
        if(Optional.ofNullable(employeeRoleMapper.getEmployeeRole(employeeId, roleId)).isPresent()){
            employeeRoleMapper.deleteEmployeeRole(employeeId, roleId);
            return Result.success("删除员工角色成功");
        }
        return Result.error("该员工角色不存在,无法删除");
    }
}
