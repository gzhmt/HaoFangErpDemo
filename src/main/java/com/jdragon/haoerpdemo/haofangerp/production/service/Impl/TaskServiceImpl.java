package com.jdragon.haoerpdemo.haofangerp.production.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.PlanStateEnum;
import com.jdragon.haoerpdemo.haofangerp.commons.tools.Bean2Utils;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.TaskVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Task;
import com.jdragon.haoerpdemo.haofangerp.production.mappers.TaskMapper;
import com.jdragon.haoerpdemo.haofangerp.production.service.PlanService;
import com.jdragon.haoerpdemo.haofangerp.production.service.TaskService;
import com.jdragon.haoerpdemo.haofangerp.security.commons.SecurityContextHolderHelper;
import org.assertj.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 17:16
 * @Description: 生产任务服务接口实现类
 */
@CacheConfig(cacheNames = "task")
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper,Task> implements TaskService {

    @Override
    public IPage<Task> list(Page<Task> page) {
        return baseMapper.selectPage(page,null);
    }

    @CachePut(key = "#result.id")
    @Override
    public Task save(TaskVo taskVo) throws Exception{
        // 从SC安全上下文获取并设置操作人员工号
        taskVo.setOperatorEmployeeNo(SecurityContextHolderHelper.getEmployeeNo());
        // 设置生产日期
        taskVo.setProductionDate(DateUtil.now());
        Task task = (Task)Bean2Utils.copyProperties(taskVo,Task.class);
        // 根据生产计划Id获取state状态
        PlanStateEnum state = baseMapper.selectPlanByPlanId(task.getProductionPlanId());
        if (state == null) {
            throw new Exception("没有相应生产计划");
        }
        if (state == PlanStateEnum.新计划 || state == PlanStateEnum.被驳回 ) {
            throw new Exception("生产计划未通过审核");
        }
        // 创建任务
        if (Optional.ofNullable(task).isPresent() && task.insert()) {
            return task;
        } else {
            throw new Exception("创建任务失败");
        }
    }

    @CacheEvict
    @Override
    public boolean delete(Long id) {
        return baseMapper.deleteById(id)>0;
    }

    @CachePut(key = "#id")
    @Override
    public boolean update(TaskVo taskVo) {
        Task task = (Task)Bean2Utils.copyProperties(taskVo,Task.class);
        return task.updateById();
    }
}
