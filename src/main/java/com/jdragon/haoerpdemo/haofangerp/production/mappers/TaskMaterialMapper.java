package com.jdragon.haoerpdemo.haofangerp.production.mappers;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Task;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.TaskMaterial;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Auther: kimid
 * @Date: 2020/3/27 01:26
 * @Description:
 */
@Mapper
@Repository
public interface TaskMaterialMapper extends BaseMapper<TaskMaterial> {


}