package com.jdragon.haoerpdemo.haofangerp.production.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdragon.haoerpdemo.haofangerp.production.constant.PlanAuditStatusEnum;
import com.jdragon.haoerpdemo.haofangerp.production.constant.PlanStateEnum;
import com.jdragon.haoerpdemo.haofangerp.production.constant.TaskStateEnum;
import com.jdragon.haoerpdemo.haofangerp.commons.tools.AutoGenerateUtil;
import com.jdragon.haoerpdemo.haofangerp.commons.tools.Bean2Utils;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.*;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.TaskMaterialVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.TaskProductVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.TaskVo;
import com.jdragon.haoerpdemo.haofangerp.production.mappers.*;
import com.jdragon.haoerpdemo.haofangerp.production.service.PlanService;
import com.jdragon.haoerpdemo.haofangerp.production.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 17:16
 * @Description: 生产任务服务接口实现类
 */
@CacheConfig(cacheNames = "task")
@Slf4j
@Service
@Transactional
public class TaskServiceImpl extends ServiceImpl<TaskMapper,Task> implements TaskService {

    @Autowired
    private PlanService planService;

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public Task queryTaskDetail(String taskNo) throws Exception{
        if (Optional.ofNullable(taskNo).isPresent()) {
            LambdaQueryWrapper<Task> planLambdaQueryWrapper = new LambdaQueryWrapper<>();
            planLambdaQueryWrapper.eq(Task::getTaskNo, taskNo);
            Task task = this.getOne(planLambdaQueryWrapper);
            if (Optional.ofNullable(task).isPresent()) {
                return task;
            } else {
                throw new Exception("没有这个生产单号的任务");
            }
        } else {
            throw new Exception("任务编号不能为空");
        }
    }

    @Override
    public IPage<Task> list(Page<Task> page) throws Exception {
        return baseMapper.selectPage(page,null);
    }

    @CachePut(key = "#result.taskNo")
    @Override
    public Task save(TaskVo taskVo) throws Exception{
        synchronized (this) {
            String productionPlanNo = taskVo.getProductionPlanNo();
            // 如果生产计划单号不存在，则抛出异常
            Plan plan = planService.getByProductionNo(productionPlanNo);
            // 判断生产计划是否通过审核
            PlanStateEnum state = plan.getState();
            PlanAuditStatusEnum auditStatus= plan.getAuditStatus();
            if ( state == PlanStateEnum.新计划  || auditStatus == PlanAuditStatusEnum.被驳回 ) {
                throw new Exception("该生产单号计划未通过审核");
            }
            // 验证货品是否存在
            // 标识哪些货品不存在
            int flag = 0;
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < taskVo.getTaskMaterialVos().size(); i++) {
                TaskMaterialVo taskMaterialVo = taskVo.getTaskMaterialVos().get(i);
                LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Goods::getGoodsNo, taskMaterialVo.getMaterialNo());
                if ( !Optional.ofNullable(goodsMapper.selectOne(queryWrapper)).isPresent() ) {
                    stringBuffer.append(taskMaterialVo.getMaterialNo() + "材料不存在");
                    flag++;
                }
            }
            for (int i = 0; i < taskVo.getTaskProductVos().size(); i++) {
                TaskProductVo taskProductVo = taskVo.getTaskProductVos().get(i);
                LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Goods::getGoodsNo,taskProductVo.getProductNo());
                if ( !Optional.ofNullable(goodsMapper.selectOne(queryWrapper)).isPresent() ){
                    stringBuffer.append(taskProductVo.getProductNo()+"原料不存在");
                    flag++;
                }
            }
            // 货品不存在则抛出异常
            if ( flag > 0 ) {
                log.info(stringBuffer.toString());
                throw new Exception(stringBuffer.toString());
            }

            // 生成任务单号
            LambdaQueryWrapper<Task> taskLambdaQueryWrapper = new LambdaQueryWrapper<>();
            taskLambdaQueryWrapper.orderByDesc(Task::getId).last("limit 1");
            Task lastTask = baseMapper.selectOne(taskLambdaQueryWrapper);
            String taskNo = null;
            if (Optional.ofNullable(lastTask).isPresent()) {
                taskNo = AutoGenerateUtil.createIncreaseOdd(lastTask.getTaskNo());
            } else {
                taskNo = AutoGenerateUtil.createTodayFirstOdd("SC");
            }
            // 获取任务实体类

            taskVo.setSequenceId(taskNo);
            taskVo.setState(TaskStateEnum.正常);
            taskVo.setStateChangeDate(DateUtil.now());
            taskVo.setCreateDate(DateUtil.now());
            Task task = (Task) Bean2Utils.copyProperties(taskVo, Task.class);
            task.setTaskNo(taskNo);
            // 创建任务
            if (task !=null && task.insert()) {
                for (int i = 0; i < taskVo.getTaskProductVos().size(); i++) {
                    TaskProduct taskProduct = (TaskProduct) Bean2Utils.copyProperties(taskVo.getTaskProductVos().get(i), TaskProduct.class);
                    taskProduct.setTaskNo(taskNo);
                    if (taskProduct == null || !taskProduct.insert()) {
                        throw new Exception(taskVo.getTaskProductVos().get(i).getProductNo()+"关系创建失败");
                    }
                }
                for (int i = 0; i < taskVo.getTaskMaterialVos().size(); i++) {
                    TaskMaterial taskMaterial = (TaskMaterial) Bean2Utils.copyProperties(taskVo.getTaskMaterialVos().get(i), TaskMaterial.class);
                    taskMaterial.setTaskNo(taskNo);
                    if (taskMaterial == null || !taskMaterial.insert()) {
                        throw new Exception(taskVo.getTaskProductVos().get(i).getProductNo()+"关系创建失败");
                    }
                }
                return task;
            } else {
                throw new Exception("创建任务失败");
            }
        }
    }

    @CacheEvict
    @Override
    public boolean delete(String[] taskNo) throws Exception{
        if (Optional.ofNullable(taskNo).isPresent()) {
            LambdaQueryWrapper<Task> planLambdaQueryWrapper = new LambdaQueryWrapper<>();
            for (int i = 0; i < taskNo.length; i++) {
                planLambdaQueryWrapper.eq(Task::getTaskNo,taskNo[i]);
                if (i != taskNo.length - 1){
                    planLambdaQueryWrapper.or();
                }
            }
            if (baseMapper.delete(planLambdaQueryWrapper) > 0) {
                return true;
            } else {
                throw new Exception("删除任务失败");
            }
        } else {
            throw new Exception("任务工号不为空");
        }
    }

    @CachePut(key = "#taskVo")
    @Override
    public boolean update(String taskNo, TaskVo taskVo) throws Exception {
        if ( "".equals(taskNo) || taskNo ==null) {
            throw new Exception("任务编号不能为空");
        }
        Task task = (Task)Bean2Utils.copyProperties(taskVo, Task.class);
        LambdaQueryWrapper<Task> taskLambdaQueryWrapper = new LambdaQueryWrapper<>();
        taskLambdaQueryWrapper.eq(Task::getTaskNo,taskNo);
        task.setProductionPlanNo(this.queryTaskDetail(taskNo).getProductionPlanNo());
        if (baseMapper.update(task, taskLambdaQueryWrapper) > 0) {
            return true;
        } else {
            throw new Exception("修改任务失败");
        }
    }

}
