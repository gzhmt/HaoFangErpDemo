package com.jdragon.haoerpdemo.haofangerp.examine.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * 计划审核DAO
 *
 * @author 金柏宇
 * @version 1.0
 * @date 2020/3/26 22:30
 */
@Repository
@Mapper
public interface PlanExamineMapper extends BaseMapper<Plan> {

    //    /**
//     * 分页获取计划
//     *
//     * @param offset 起始索引
//     * @param count 记录数
//     * @return 按参数查询数据库得到特定范围的数据
//    List<Plan> getPlanByPagging(@Param("offset") long offset,@Param("count") long count);
//
//    *//**
//     * 根据审核请求进行状态更新
//     *
//     * @param productionNo
//     * @param examineCode
//     * @return
//     *//*
    @Update("UPDATE production_plan SET audit_status=#{examineCode} WHERE deleted=FALSE AND production_no=#{productionNo,jdbcType=VARCHAR}")
    int updateState(@Param("productionNo") String productionNo, @Param("examineCode") int examineCode);
//
//    *//**
//     *
//     * @return 返回总记录数
//     *//*
//    long totalCount();*/
}
