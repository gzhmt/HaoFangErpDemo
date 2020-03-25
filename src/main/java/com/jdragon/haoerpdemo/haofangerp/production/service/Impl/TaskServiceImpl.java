package com.jdragon.haoerpdemo.haofangerp.production.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.PlanStateEnum;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.TaskStateEnum;
import com.jdragon.haoerpdemo.haofangerp.commons.tools.AutoGenerateUtil;
import com.jdragon.haoerpdemo.haofangerp.commons.tools.Bean2Utils;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.PlanVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.TaskVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Task;
import com.jdragon.haoerpdemo.haofangerp.production.mappers.PlanMapper;
import com.jdragon.haoerpdemo.haofangerp.production.mappers.TaskMapper;
import com.jdragon.haoerpdemo.haofangerp.production.service.PlanService;
import com.jdragon.haoerpdemo.haofangerp.production.service.TaskService;
import com.jdragon.haoerpdemo.haofangerp.security.commons.SecurityContextHolderHelper;
import org.assertj.core.util.DateUtil;
import org.omg.PortableServer.POAPackage.ObjectNotActiveHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    @Autowired
    private PlanServiceImpl planServiceImpl;

    @Override
    public Task queryTaskDetail(String taskNo) throws Exception{
        LambdaQueryWrapper<Task> planLambdaQueryWrapper = new LambdaQueryWrapper<>();
        planLambdaQueryWrapper.eq(Task::getTaskNo,taskNo);
        Task task = this.getOne(planLambdaQueryWrapper);
        if(Optional.ofNullable(task).isPresent()){
            return task;
        }else{
            throw new Exception("没有这个生产单号的任务");
        }
    }

    @Override
    public IPage<Task> list(Page<Task> page) {
        return baseMapper.selectPage(page,null);
    }

    @CachePut(key = "#result.id")
    @Override
    public Task save(TaskVo taskVo) throws Exception{
        synchronized (this) {
            String productionPlanNo = taskVo.getProductionPlanNo();
            // 如果生产计划单号不存在，则抛出异常
            Plan plan = planServiceImpl.getByProductionNo(productionPlanNo);
            // 判断生产计划是否通过审核
            PlanStateEnum state = plan.getState();
            if (state == PlanStateEnum.新计划 || state == PlanStateEnum.被驳回) {
                throw new Exception("该生产单号计划未通过审核");
            }
            // 获取并设置任务单号
            LambdaQueryWrapper<Task> taskLambdaQueryWrapper = new LambdaQueryWrapper<>();
            taskLambdaQueryWrapper.orderByDesc(Task::getId).last("limit 1");
            Task lastTask = baseMapper.selectOne(taskLambdaQueryWrapper);
            String taskNo = null;
            if (lastTask == null) {
                taskNo = AutoGenerateUtil.createTodayFirstOdd(lastTask.getTaskNo());

            } else {
                taskNo = AutoGenerateUtil.createTodayFirstOdd("SC");
            }
            taskVo.setTaskNo(taskNo);
            taskVo.setSequenceId(taskNo);
            taskVo.setOperatorEmployeeNo(SecurityContextHolderHelper.getEmployeeNo());
            taskVo.setProductionPlanId(plan.getId());
            taskVo.setState(TaskStateEnum.正常);
            taskVo.setStateChangeDate(DateUtil.now());
            taskVo.setCreateDate(DateUtil.now());

            // 获得实体类
            Task task = (Task) Bean2Utils.copyProperties(taskVo, Task.class);

            // 创建任务
            if (task !=null && task.insert()) {
                return task;
            } else {
                throw new Exception("创建任务失败");
            }
        }
    }

    @CacheEvict
    @Override
    public boolean delete(Long id) throws Exception{
        if (baseMapper.deleteById(id) > 0) {
            return true;
        } else {
            throw new Exception("删除任务失败");
        }
    }

    @CachePut(key = "#id")
    @Override
    public boolean update(TaskVo taskVo) throws Exception {
        Task task = (Task)Bean2Utils.copyProperties(taskVo, Task.class);
        task.setState(baseMapper.selectById(taskVo.getId()).getState());
        if (baseMapper.updateById(task) > 0) {
            return true;
        } else {
            throw new Exception("删除任务失败");
        }
    }

}
