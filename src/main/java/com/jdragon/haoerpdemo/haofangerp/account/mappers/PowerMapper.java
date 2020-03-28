package com.jdragon.haoerpdemo.haofangerp.account.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Power;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Role;
import com.jdragon.haoerpdemo.haofangerp.account.domain.provider.PowerProvider;
import com.jdragon.haoerpdemo.haofangerp.account.domain.provider.RoleProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午4:10
 * @Description: 权限仓储层
 */
@Mapper
@Repository
public interface PowerMapper extends BaseMapper<Power> {

    @SelectProvider(type = PowerProvider.class,method = "powerByEmployeeNo")
    List<Power> getPowerByEmployeeNo(String employeeNo);

    /**
     * 获取权限列表(按权重从大到小排列)
     * @return
     */
    @Select("select * from system_power p order by p.api_sort desc")
    List<Power> listPowers();

    /**
     * 根据角色id获取已赋予权限列表
     * @param roleId 角色id
     * @return
     */
    @SelectProvider(type = PowerProvider.class,method = "getAssignedPowersByRoleId")
    List<Power> getAssignedPowersByRoleId(int roleId);


    /**
     * 根据角色id和接口url判断此角色是否拥有此权限
     * @param roleId 角色id
     * @param apiUrl 接口url
     * @return 若无此权限则返回0
     */
    @Select("select count(*) from system_power p" +
            " join system_role_power rp on p.id=rp.power_id" +
            " where rp.role_id=#{roleId} and p.api_url=#{apiUrl}")
    int getCountByRoleIdAndApiUrl(int roleId, String apiUrl);


    /**
     * 根据角色id获取未赋予权限列表
     * @param roleId 角色id
     * @return
     */
    @Select("select * from system_power p where p.id not in" +
            " (select p.id from system_power p" +
            " join system_role_power rp on p.id=rp.power_id" +
            " join system_role r on r.id=rp.role_id" +
            " where r.id=#{roleId}) order by p.api_sort desc")
    List<Power> getUnAssignedPowersByRoleId(int roleId);
}
