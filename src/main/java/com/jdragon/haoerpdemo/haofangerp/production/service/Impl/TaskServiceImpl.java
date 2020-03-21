package com.jdragon.haoerpdemo.haofangerp.production.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdragon.haoerpdemo.haofangerp.commons.tools.Bean2Utils;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.TaskVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Task;
import com.jdragon.haoerpdemo.haofangerp.production.mappers.TaskMapper;
import com.jdragon.haoerpdemo.haofangerp.production.service.TaskService;
import org.springframework.stereotype.Service;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 17:16
 * @Description: 生产任务服务接口实现类
 */

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper,Task> implements TaskService {

    @Override
    public IPage<Task> list(Page<Task> page) {
        return baseMapper.selectPage(page,null);
    }

    @Override
    public Task save(TaskVo taskVo) {
        Task task = (Task)Bean2Utils.copyProperties(taskVo,Task.class);

        return task.insert()?task:null;
    }

    @Override
    public boolean delete(Long id) {
        return baseMapper.deleteById(id)>0;
    }

    @Override
    public boolean update(TaskVo taskVo) {
        Task task = (Task)Bean2Utils.copyProperties(taskVo,Task.class);
        return task.updateById();
    }
}
