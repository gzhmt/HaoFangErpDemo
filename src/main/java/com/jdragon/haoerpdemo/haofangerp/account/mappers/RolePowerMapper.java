package com.jdragon.haoerpdemo.haofangerp.account.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.EmployeeRole;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.RolePower;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/28 下午2:04
 * @Description
 */
@Mapper
@Repository
public interface RolePowerMapper extends BaseMapper<RolePower> {

    /**
     * 根据角色id和权限id查询角色权限信息
     * @param roleId 角色id
     * @param powerId 权限id
     * @return
     */
    @Select("select * from system_role_power where role_id = #{roleId} and power_id = #{powerId}")
    RolePower getRolePower(int roleId, int powerId);

    /**
     * 根据角色id删除其所有权限信息
     * @param roleId 角色id
     * @return
     */
    @Delete("delete from system_role_power where role_id = #{roleId}")
    int deleteByRoleId(int roleId);

    /**
     * 删除角色权限关系
     * @param roleId 角色id
     * @param powerId 权限id
     * @return
     */
    @Delete("delete from system_role_power where role_id = #{roleId} and power_id = #{powerId}")
    int deleteRolePower(int roleId, int powerId);
}
