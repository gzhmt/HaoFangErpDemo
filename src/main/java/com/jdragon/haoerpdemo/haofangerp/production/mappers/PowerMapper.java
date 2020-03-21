package com.jdragon.haoerpdemo.haofangerp.production.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Power;
import com.jdragon.haoerpdemo.haofangerp.production.domain.provider.PowerProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.20 11:22
 * @Description: 权限仓储层
 */
@Mapper
@Repository
public interface PowerMapper extends BaseMapper<Power> {

    @SelectProvider(type = PowerProvider.class,method = "powerByEmployeeNo")
    List<Power> getPowerByEmployeeNo(String employeeNo);

}
