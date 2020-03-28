package com.jdragon.haoerpdemo.haofangerp.examine.service.impl;

import com.jdragon.haoerpdemo.haofangerp.examine.component.PaggingBean;
import com.jdragon.haoerpdemo.haofangerp.examine.component.PaggingParams;
import com.jdragon.haoerpdemo.haofangerp.examine.component.exceptions.PageSizeException;
import com.jdragon.haoerpdemo.haofangerp.examine.component.exceptions.TotalException;
import com.jdragon.haoerpdemo.haofangerp.examine.dao.TaskInfoDao;
import com.jdragon.haoerpdemo.haofangerp.examine.service.TaskInfoService;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.TaskMaterialVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.TaskProductVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 金柏宇
 * @version 1.0
 * @date 2020/3/26 22:26
 */
@Service
public class TaskInfoServiceImpl implements TaskInfoService {
    @Autowired
    private TaskInfoDao taskInfoDao;


    @Override
    public List<TaskVo> getTaskByPagging(String productionNo, PaggingParams params,long total) throws PageSizeException, TotalException {
        PaggingBean paggingBean=new PaggingBean(params,total);
        //分页查询出任务列表
        List<TaskVo> taskList=taskInfoDao.getTaskByPagging(productionNo,paggingBean.getOffset(),paggingBean.getCorrectPageSize());
        if(!taskList.isEmpty()) {
            taskList.parallelStream().forEach(taskVo -> {
                //用任务编号查询对应原料和成品信息
                String taskNo = taskVo.getTaskNo();
                List<TaskMaterialVo> taskMaterials = taskInfoDao.getTaskMaterialByTaskNo(taskNo);
                List<TaskProductVo> taskProducts = taskInfoDao.getTaskProductByTaskNo(taskNo);
                taskVo.setTaskMaterialVos(taskMaterials);
                taskVo.setTaskProductVos(taskProducts);
            });
        }
        return taskList;
    }

    @Override
    public long getTaskToal() {
        return taskInfoDao.getTaskTotal();
    }
}