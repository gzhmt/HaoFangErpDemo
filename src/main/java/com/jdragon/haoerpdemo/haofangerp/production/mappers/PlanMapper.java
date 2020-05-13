package com.jdragon.haoerpdemo.haofangerp.production.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 17:04
 * @Description: 生产计划仓储层
 */
@Mapper
@Repository
public interface PlanMapper extends BaseMapper<Plan> {

    @Select("select * from production_plan where production_no=#{productionNo} and deleted=false")
    Plan selectByProductionNo(String productionNo);

    @Select("select * from production_plan order by id Desc limit 1")
    Plan selectByIdDescLimitOne();

    @Select("select production_no from production_plan where production_no like #{productionNo}")
    List<String> selectFuzzyPlanName(String productionNo);
}
