package com.jdragon.haoerpdemo.haofangerp.production.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.TaskVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Task;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 17:15
 * @Description: 生产任务服务接口类
 */

public interface TaskService extends IService<Task> {
    IPage<Task> list(Page<Task> page) throws Exception;
    Task save(TaskVo taskVo) throws Exception;
    boolean delete(String[] taskNo) throws Exception;
    boolean update(String taskNo, TaskVo taskVo) throws Exception;
    Task queryTaskDetail(String taskNo) throws Exception;
}
