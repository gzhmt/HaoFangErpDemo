package com.jdragon.haoerpdemo.haofangerp.account.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Power;
import com.jdragon.haoerpdemo.haofangerp.account.domain.provider.PowerProvider;
import org.apache.ibatis.annotations.Mapper;
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

}
