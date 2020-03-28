package com.jdragon.haoerpdemo.haofangerp.examine.dao;

import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.TaskMaterialVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.TaskProductVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.TaskVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 任务信息列表DAO
 *
 * @author 金柏宇
 * @version 1.0
 * @date 2020/3/26 22:31
 */
@Repository
public interface TaskInfoDao {
    /**
     * 根据生产计划编号获取所属任务列表信息
     *
     * @param productionNo 生产计划编号
     * @return 对应生产计划编号的任务列表
     */
    List<TaskVo> getTaskByPagging(@Param("productionNo") String productionNo, @Param("offset") long offset, @Param("count") long count);

    /**
     * 根据任务编号获取对应原料信息
     *
     * @param taskNo 任务编号
     * @return 对应任务编号的 原料 列表
     */
    List<TaskMaterialVo> getTaskMaterialByTaskNo(String taskNo);

    /**
     * 根据任务编号获取对应成品信息
     *
     * @param taskNo 任务编号
     * @return 对应任务编号的 成品/半成品 列表
     */
    List<TaskProductVo> getTaskProductByTaskNo(String taskNo);

    /**
     * 获取任务总记录数
     *
     * @return
     */
    long getTaskTotal();

}
