package com.jdragon.haoerpdemo.haofangerp.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Employee;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.EmployeeRole;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Role;
import com.jdragon.haoerpdemo.haofangerp.account.domain.vo.RoleVo;
import com.jdragon.haoerpdemo.haofangerp.account.mappers.EmployeeRoleMapper;
import com.jdragon.haoerpdemo.haofangerp.account.mappers.RoleMapper;
import com.jdragon.haoerpdemo.haofangerp.account.mappers.RolePowerMapper;
import com.jdragon.haoerpdemo.haofangerp.account.service.RoleService;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Task;
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

    @Autowired
    private RolePowerMapper rolePowerMapper;

    @Override
    public List<String> getRolesByEmployeeNo(String employeeNo) {
        return baseMapper.getRolesByEmployeeNo(employeeNo);
    }

    @Override
    public IPage<Role> listRoles(Page<Role> page) {
        return baseMapper.selectPage(page,null);
    }

    @Override
    public Role addRole(RoleVo roleVo) throws Exception{
        String prefix = "ROLE_";
        String roleName = prefix + roleVo.getRoleName().toUpperCase();
        LambdaQueryWrapper<Role> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Role::getRoleName, roleName);
        if(Optional.ofNullable(this.getOne(lambdaQueryWrapper)).isPresent()) {
            throw new Exception("该角色已存在");
        }else {
            Role role = new Role();
            role.setRoleName(roleName);
            role.setRoleDescribe(roleVo.getRoleDescribe());
            if(role.insert()){
                return role;
            }else {
                throw new Exception("添加角色失败");
            }
        }
    }

    @Override
    public boolean deleteRole(int roleId) throws Exception {
        if(roleMapper.getEmployeeRoleCountByRoleId(roleId) == 0){
            if(roleMapper.deleteById(roleId) > 0){
                rolePowerMapper.deleteByRoleId(roleId);
                return true;
            }else {
                throw new Exception("无该角色,删除失败");
            }
        }else {
            throw new Exception("该角色被员工依赖,无法删除");
        }
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
    public boolean addRoleOfEmployee(int employeeId, int roleId) throws Exception{
        if(!Optional.ofNullable(employeeRoleMapper.getEmployRole(employeeId, roleId)).isPresent()){
            EmployeeRole employeeRole = new EmployeeRole();
            employeeRole.setEmployeeId(employeeId);
            employeeRole.setRoleId(roleId);
            if(employeeRole.insert()){
                return true;
            }else {
                throw new Exception("添加员工角色失败");
            }
        }else {
            throw new Exception("该员工角色已存在");
        }
    }

    @Override
    public boolean deleteRoleOfEmployee(int employeeId, int roleId) throws Exception {
        if (employeeRoleMapper.deleteEmployRole(employeeId, roleId) > 0) {
            return true;
        } else {
            throw new Exception("无该员工角色关系,无法删除");
        }
    }
}
