package com.jdragon.haoerpdemo.haofangerp.production.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdragon.haoerpdemo.haofangerp.production.constant.PlanAuditStatusEnum;
import com.jdragon.haoerpdemo.haofangerp.production.constant.TaskStateEnum;
import com.jdragon.haoerpdemo.haofangerp.commons.tools.AutoGenerateUtil;
import com.jdragon.haoerpdemo.haofangerp.commons.tools.Bean2Utils;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.*;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.task.*;
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

import javax.validation.constraints.NotNull;
import java.util.List;
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
public class TaskServiceImpl extends ServiceImpl<TaskMapper,Task> implements TaskService {

    @Autowired
    private PlanService planService;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private TaskMaterialMapper taskMaterialMapper;

    @Autowired
   private TaskProductMapper taskProductMapper;

    @Override
    public TaskDetailVo queryTaskDetail(String taskNo) throws Exception{
        // 通过任务编号查询任务详情页
        if (Optional.ofNullable(taskNo).isPresent()) {
            LambdaQueryWrapper<Task> planLambdaQueryWrapper = new LambdaQueryWrapper<>();
            planLambdaQueryWrapper.eq(Task::getTaskNo, taskNo);
            Task task = this.getOne(planLambdaQueryWrapper);
            if (Optional.ofNullable(task).isPresent()) {
                List<TaskMaterialGoodsVo> taskMaterialGoodsVos = taskMaterialMapper.selectMaterialAndGoodsByTaskNo(taskNo);
                List<TaskProductGoodsVo> taskProductGoodsVos = taskProductMapper.selectProductAndGoodsByTaskNo(taskNo);
                if (Optional.ofNullable(taskMaterialGoodsVos).isPresent() && Optional.ofNullable(taskProductGoodsVos).isPresent())  {
                    TaskDetailVo taskDetailVo = (TaskDetailVo)Bean2Utils.copyProperties(task, TaskDetailVo.class);
                    taskDetailVo.setTaskMaterialGoodsVos(taskMaterialGoodsVos);
                    taskDetailVo.setTaskProductGoodsVos(taskProductGoodsVos);
                    return taskDetailVo;
                } else {
                    throw new UnknownError("材料或原料获取失败");
                }
            } else {
                throw new Exception("该任务编号不存在");
            }
        } else {
            throw new Exception("任务编号不能为空");
        }
    }

    @Override
    public IPage<Task> list(Page<Task> page,String planNo) throws Exception {
        return baseMapper.selectPage(page,new LambdaQueryWrapper<Task>().eq(Task::getProductionPlanNo,planNo));
    }

    @Transactional
    @CachePut(key = "#result.taskNo")
    @Override
    public Task save(TaskInsertVo taskInsertVo) throws Exception{
        synchronized (this) {
            String productionPlanNo = taskInsertVo.getProductionPlanNo();
            // 如果生产计划单号不存在，则抛出异常
            Plan plan = planService.getByProductionNo(productionPlanNo);
            // 判断生产计划是否通过审核
            PlanAuditStatusEnum auditStatus= plan.getAuditStatus();
            if ( auditStatus != PlanAuditStatusEnum.审核通过 ) {
                throw new Exception("该生产单号计划未通过审核");
            }
            // 查询原料和成品是否存在，不存在则抛出异常
            queryGoodsExist(taskInsertVo);
            // 生成任务单号
            String taskNo = createTaskNo();
            // 获取任务实体类
            Task task = (Task) Bean2Utils.copyProperties(taskInsertVo, Task.class);
            // 暂时假设流水线与任务编号后缀一致
            task.setSequenceId(taskNo.replace("RW","LS"));
            task.setTaskNo(taskNo);
            task.setState(TaskStateEnum.正常);
            task.setStateChangeDate(DateUtil.now());
            task.setCreateDate(DateUtil.now());
            // 创建任务
            if (task.insert()) {
                for (int i = 0; i < taskInsertVo.getTaskProductVos().size(); i++) {
                    // 创建任务成品关系
                    TaskProduct taskProduct = (TaskProduct) Bean2Utils.copyProperties(taskInsertVo.getTaskProductVos().get(i), TaskProduct.class);
                    taskProduct.setTaskNo(taskNo);
                    if (!taskProduct.insert()) {
                        throw new UnknownError(taskInsertVo.getTaskProductVos().get(i).getProductNo()+"关系创建失败");
                    }
                }
                for (int i = 0; i < taskInsertVo.getTaskMaterialVos().size(); i++) {
                    // 创建任务原料关系
                    TaskMaterial taskMaterial = (TaskMaterial) Bean2Utils.copyProperties(taskInsertVo.getTaskMaterialVos().get(i), TaskMaterial.class);
                    taskMaterial.setTaskNo(taskNo);
                    if (!taskMaterial.insert()) {
                        throw new UnknownError(taskInsertVo.getTaskProductVos().get(i).getProductNo()+"关系创建失败");
                    }
                }
                // 返回任务简略项
                return task;
            } else {
                throw new UnknownError("创建任务失败");
            }
        }
    }
    /**
     * @Author kimi
     * @Description:生成任务编码
     * @Date 2020/3/29 21:51
     * @Param []
     * @return java.lang.String
     **/
    public String createTaskNo() throws  Exception{
        LambdaQueryWrapper<Task> taskLambdaQueryWrapper = new LambdaQueryWrapper<>();
        taskLambdaQueryWrapper.orderByDesc(Task::getId).last("limit 1");
        Task lastTask = baseMapper.selectOne(taskLambdaQueryWrapper);
        String taskNo = null;
        if (Optional.ofNullable(lastTask).isPresent()) {
            return AutoGenerateUtil.createIncreaseOdd(lastTask.getTaskNo());
        } else {
            return AutoGenerateUtil.createTodayFirstOdd("RW");
        }
    }
    /**
     * @Author kimi
     * @Description:查询材料和成品是否存在
     * @Date 2020/3/29 21:52
     * @Param [baseTaskVo]
     * @return boolean
     **/
    public boolean queryGoodsExist(BaseTaskVo baseTaskVo) throws Exception{
        // 验证货品是否存在
        // 标识哪些货品不存在
        int flag = 0;
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < baseTaskVo.getTaskMaterialVos().size(); i++) {
            // 构建queryWrapper
            TaskMaterialVo taskMaterialVo = baseTaskVo.getTaskMaterialVos().get(i);
            LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Goods::getGoodsNo, taskMaterialVo.getMaterialNo());
            // 查询
            if ( !Optional.ofNullable(goodsMapper.selectOne(queryWrapper)).isPresent() ) {
                stringBuffer.append(taskMaterialVo.getMaterialNo() + "材料不存在");
                flag++;
            }
        }
        for (int i = 0; i < baseTaskVo.getTaskProductVos().size(); i++) {
            // 构建queryWrapper
            TaskProductVo taskProductVo = baseTaskVo.getTaskProductVos().get(i);
            LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Goods::getGoodsNo,taskProductVo.getProductNo());
            // 查询
            if ( !Optional.ofNullable(goodsMapper.selectOne(queryWrapper)).isPresent() ){
                stringBuffer.append(taskProductVo.getProductNo()+"原料不存在");
                flag++;
            }
        }
        // 货品不存在则抛出异常
        if ( flag > 0 ) {
            throw new Exception(stringBuffer.toString());
        } else {
            return true;
        }
    }

    @CacheEvict
    @Override
    public boolean delete(String[] taskNos) throws Exception{
        if (!Optional.ofNullable(taskNos).isPresent() || taskNos.length == 0) {
            throw new Exception("任务编号不为空");
        }
        // 查询任务是否存在否则抛出异常
        for (int i = 0; i < taskNos.length; i++) {
            getByTaskNo(taskNos[i]);
        }
        // 构造wrapper
        LambdaQueryWrapper<Task> planLambdaQueryWrapper = new LambdaQueryWrapper<>();
        for (int i = 0; i < taskNos.length; i++) {
            planLambdaQueryWrapper.eq(Task::getTaskNo,taskNos[i]);
            if (i != taskNos.length - 1){
                planLambdaQueryWrapper.or();
            }
        }
        // 删除任务（逻辑删除）
        if (baseMapper.delete(planLambdaQueryWrapper) > 0) {
            return true;
        } else {
            throw new UnknownError("删除任务失败");
        }
    }

    @CachePut(key = "#taskVo")
    @Override
    public boolean update(TaskUpdateVo taskUpdateVo) throws Exception {
        // 查询任务编号是否存在，不存在则报错
        getByTaskNo(taskUpdateVo.getTaskNo());
        // 查询原料和成品是否存在，不存在则抛出异常
        queryGoodsExist(taskUpdateVo);
        Task task = (Task)Bean2Utils.copyProperties(taskUpdateVo, Task.class);
        // 获取任务编号
        String taskNo = taskUpdateVo.getTaskNo();
        // 状态改变就设置新的状态改变时间
        if (!getByTaskNo(taskNo).getState().equals(taskUpdateVo.getState())) {
            task.setStateChangeDate(DateUtil.now());
        }
        // 构造queryWrapper
        LambdaQueryWrapper<Task> taskLambdaQueryWrapper = new LambdaQueryWrapper<>();
        taskLambdaQueryWrapper.eq(Task::getTaskNo, taskNo);
        // 修改任务
        if (baseMapper.update(task, taskLambdaQueryWrapper) > 0) {
            for (int i = 0; i < taskUpdateVo.getTaskProductVos().size(); i++) {
                // 创建任务成品关系
                TaskProduct taskProduct = (TaskProduct) Bean2Utils.copyProperties(taskUpdateVo.getTaskProductVos().get(i), TaskProduct.class);
                taskProduct.setTaskNo(taskNo);
                // 从任务编号和成品编号角度查询是否存在
                LambdaQueryWrapper<TaskProduct> taskProductLambdaQueryWrapper = new LambdaQueryWrapper<>();
                taskProductLambdaQueryWrapper.eq(TaskProduct::getTaskNo, taskNo)
                        .eq(TaskProduct::getProductNo, taskProduct.getProductNo());

            }
            for (int i = 0; i < taskUpdateVo.getTaskMaterialVos().size(); i++) {
                // 创建任务原料关系
                TaskMaterial taskMaterial = (TaskMaterial) Bean2Utils.copyProperties(taskUpdateVo.getTaskMaterialVos().get(i), TaskMaterial.class);
                taskMaterial.setTaskNo(taskNo);
                if (!Optional.ofNullable(taskMaterial).isPresent() || !taskMaterial.insert()) {
                    throw new Exception(taskUpdateVo.getTaskProductVos().get(i).getProductNo()+"关系创建失败");
                }
            }
            return true;
        } else {
            throw new Exception("修改任务失败");
        }
    }

//    public boolean updateOrInsertTaskRelation(Object obj,LambdaQueryWrapper lambdaQueryWrapper){
//        if (Optional.ofNullable(obj).isPresent()) {
//            if (Optional.ofNullable(obj.selectOne(lambdaQueryWrapper)).isPresent()) {
//                // 存在则修改
//                if (!taskProduct.update(taskProductLambdaQueryWrapper)){
//                    throw new Exception("修改任务失败:["+taskProduct.getProductNo()+"]修改失败");
//                }
//            } else {
//                // 不存在则新增
//                if (!taskProduct.insert()){
//                    throw new Exception("修改任务失败:["+taskProduct.getProductNo()+"]添加失败");
//                }
//            }
//        } else {
//            throw new Exception("修改任务失败:["+taskUpdateVo.getTaskProductVos().get(i).getProductNo()+"]为空");
//        }
//    }

//    if (Optional.ofNullable(taskProduct).isPresent()) {
//        if (Optional.ofNullable(taskProduct.selectOne(taskProductLambdaQueryWrapper)).isPresent()) {
//            // 存在则修改
//            if (!taskProduct.update(taskProductLambdaQueryWrapper)){
//                throw new Exception("修改任务失败:["+taskProduct.getProductNo()+"]修改失败");
//            }
//        } else {
//            // 不存在则新增
//            if (!taskProduct.insert()){
//                throw new Exception("修改任务失败:["+taskProduct.getProductNo()+"]添加失败");
//            }
//        }
//    } else {
//        throw new Exception("修改任务失败:["+taskUpdateVo.getTaskProductVos().get(i).getProductNo()+"]为空");
//    }
    @Override
    public Task getByTaskNo(String taskNo) throws Exception{
        if (!Optional.ofNullable(taskNo).isPresent()) {
            throw new Exception("任务编号不能为空");
        }
        // 构造queryWrapper
        LambdaQueryWrapper<Task> taskLambdaQueryWrapper = new LambdaQueryWrapper<>();
        taskLambdaQueryWrapper.eq(Task::getTaskNo,taskNo);
        // 通过编号查询
        Task task = baseMapper.selectOne(taskLambdaQueryWrapper);
        if (Optional.ofNullable(task).isPresent()) {
            return task;
        } else {
            throw new Exception("任务编号["+taskNo+"]不存在");
        }
    }
}
