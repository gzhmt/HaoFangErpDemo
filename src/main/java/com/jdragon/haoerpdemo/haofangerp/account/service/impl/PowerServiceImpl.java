package com.jdragon.haoerpdemo.haofangerp.account.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Power;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Role;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.RolePower;
import com.jdragon.haoerpdemo.haofangerp.account.mappers.PowerMapper;
import com.jdragon.haoerpdemo.haofangerp.account.mappers.RolePowerMapper;
import com.jdragon.haoerpdemo.haofangerp.account.service.PowerService;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午4:41
 * @Description: 用户权限服务接口实现类
 */
@CacheConfig(cacheNames = "power")
@Service
public class PowerServiceImpl extends ServiceImpl<PowerMapper, Power> implements PowerService {

    @Autowired
    private PowerMapper powerMapper;

    @Autowired
    private RolePowerMapper rolePowerMapper;

    @Cacheable
    @Override
    public List<Power> getPowerByEmployeeNo(String employeeNo) {
        return baseMapper.getPowerByEmployeeNo(employeeNo);
    }

    @Override
    public IPage<Power> listPowers(Page<Power> page) {
        return baseMapper.selectPage(page,null);
    }

    @Override
    public List<Power> getAssignedPowersByRoleId(int pageNo, int pageSize, int roleId) {
        PageHelper.startPage(pageNo, pageSize);
        return powerMapper.getAssignedPowersByRoleId(roleId);
    }

    @Override
    public List<Power> getUnAssignedPowersByRoleId(int pageNo, int pageSize, int roleId) {
        if(powerMapper.getCountByRoleIdAndApiUrl(roleId, "/**") == 0){
            PageHelper.startPage(pageNo, pageSize);
            return powerMapper.getUnAssignedPowersByRoleId(roleId);
        }
        return null;
    }

    @Override
    public boolean addPowerOfRole(int roleId, int powerId) throws Exception{
        if(powerMapper.getCountByRoleIdAndApiUrl(roleId, "/**") == 0){
            if(!Optional.ofNullable(rolePowerMapper.getRolePower(roleId, powerId)).isPresent()){
                if(powerMapper.selectById(powerId).getApiUrl().equals("/**")) {
                    rolePowerMapper.deleteByRoleId(roleId);
                }
                RolePower rolePower = new RolePower();
                rolePower.setRoleId(roleId);
                rolePower.setPowerId(powerId);
                if(rolePowerMapper.insert(rolePower) > 0){
                    return true;
                }else {
                    throw new Exception("添加角色权限失败");
                }
            }else {
                throw new Exception("该角色权限已存在");
            }
        }else {
            throw new Exception("该角色已拥有全部权限,不需要再添加");
        }
    }

    @Override
    public boolean deletePowerOfRole(int roleId, int powerId) throws Exception {
        if (rolePowerMapper.deleteRolePower(roleId, powerId) > 0) {
            return true;
        } else {
            throw new Exception("无该角色权限关系,无法删除");
        }
    }
}
