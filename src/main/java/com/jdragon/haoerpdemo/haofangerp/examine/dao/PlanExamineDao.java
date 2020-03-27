package com.jdragon.haoerpdemo.haofangerp.examine.dao;

import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 计划审核DAO
 *
 * @author 金柏宇
 * @version 1.0
 * @date 2020/3/26 22:30
 */
@Repository
public interface PlanExamineDao {

    /**
     * 分页获取计划
     *
     * @param offset 起始索引
     * @param count 记录数
     * @return 按参数查询数据库得到特定范围的数据
     */
    List<Plan> getPlanByPagging(@Param("offset") long offset,@Param("count") long count);

    /**
     * 根据审核请求进行状态更新
     *
     * @param productionNo
     * @param examineCode
     * @return
     */
    int updateState(@Param("productionNo") String productionNo, @Param("examineCode") int examineCode);

    /**
     *
     * @return 返回总记录数
     */
    long totalCount();
}
