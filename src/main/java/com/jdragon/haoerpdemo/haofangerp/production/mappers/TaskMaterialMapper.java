package com.jdragon.haoerpdemo.haofangerp.production.mappers;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Task;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.TaskMaterial;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.task.TaskMaterialGoodsVo;
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
public interface TaskMaterialMapper extends BaseMapper<TaskMaterial> {

    /**
     * @Author kimi
     * @Description:联合查询材料和货品，用于查询任务详情
     * @Date 2020/3/30 17:48
     * @Param [taskNo]
     * @return java.util.List<com.jdragon.haoerpdemo.haofangerp.production.domain.vo.task.TaskMaterialGoodsVo>
     **/
    @Select("select material_no, material_number, scrap_number, ptm.remarks, " +
            "goods_name, goods_type, goods_unit, gi.remarks " +
            "from production_task_material ptm " +
            "inner join goods_info gi " +
            "on ptm.material_no = gi.goods_no " +
            "where task_no = #{taskNo}")
    List<TaskMaterialGoodsVo> selectMaterialAndGoodsByTaskNo(String taskNo);

}