package com.jdragon.haoerpdemo.haofangerp.production.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.TaskProduct;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.task.TaskMaterialGoodsVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.task.TaskProductGoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: kimid
 * @Date: 2020/3/27 01:26
 * @Description:
 */
@Mapper
@Repository
public interface TaskProductMapper extends BaseMapper<TaskProduct> {
    /**
     * @Author kimi
     * @Description:联合查询成品和货品，用于查询任务详情
     * @Date 2020/3/30 17:48
     * @Param [taskNo]
     * @return java.util.List<com.jdragon.haoerpdemo.haofangerp.production.domain.vo.task.TaskProductGoodsVo>
     **/
    @Select("select product_no, product_number, scrap_number, ptp.remarks, " +
            "goods_name, goods_type, goods_unit, gi.remarks " +
            "from production_task_product ptp " +
            "inner join goods_info gi " +
            "on ptp.product_no = gi.goods_no " +
            "where task_no = #{taskNo}")
    List<TaskProductGoodsVo> selectProductAndGoodsByTaskNo(String taskNo);
}