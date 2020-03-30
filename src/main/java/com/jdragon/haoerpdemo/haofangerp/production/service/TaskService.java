package com.jdragon.haoerpdemo.haofangerp.production.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.TaskVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Task;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.task.BaseTaskVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.task.TaskInsertVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.task.TaskUpdateVo;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 17:15
 * @Description: 生产任务服务接口类
 */

public interface TaskService extends IService<Task> {
    /**
     * @Author kimi
     * @Description:分页查询
     * @Date 2020/3/30 18:40
     * @Param [page]
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Task>
     **/
    IPage<Task> list(Page<Task> page) throws Exception;
    /**
     * @Author kimi
     * @Description:添加任务
     * @Date 2020/3/30 18:40
     * @Param [taskInsertVo]
     * @return com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Task
     **/
    Task save(TaskInsertVo taskInsertVo) throws Exception;
    /**
     * @Author kimi
     * @Description:删除任务
     * @Date 2020/3/30 18:40
     * @Param [taskNo]
     * @return boolean
     **/
    boolean delete(String[] taskNo) throws Exception;
//    /**
//     * @Author kimi
//     * @Description:更新用户
//     * @Date 2020/3/30 18:41
//     * @Param [taskUpdateVo]
//     * @return boolean
//     **/
//    boolean update(TaskUpdateVo taskUpdateVo) throws Exception;
    /**
     * @Author kimi
     * @Description:查询任务闲情页
     * @Date 2020/3/30 18:41
     * @Param [taskNo]
     * @return com.jdragon.haoerpdemo.haofangerp.production.domain.vo.task.BaseTaskVo
     **/
    BaseTaskVo queryTaskDetail(String taskNo) throws Exception;
    /**
     * @Author kimi
     * @Description:根据任务编号查询任务
     * @Date 2020/3/30 18:41
     * @Param [taskNo]
     * @return com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Task
     **/
    Task getByTaskNo(String taskNo) throws Exception;
}
