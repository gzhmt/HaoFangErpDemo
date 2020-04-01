package com.jdragon.haoerpdemo.haofangerp.examine.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdragon.haoerpdemo.haofangerp.examine.component.exceptions.PaggingParamsException;
import com.jdragon.haoerpdemo.haofangerp.examine.dao.TaskInfoDao;
import com.jdragon.haoerpdemo.haofangerp.examine.domain.vo.ExamineTaskDetailVo;
import com.jdragon.haoerpdemo.haofangerp.examine.service.TaskInfoService;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.task.*;

import com.jdragon.haoerpdemo.haofangerp.production.mappers.TaskMaterialMapper;
import com.jdragon.haoerpdemo.haofangerp.production.mappers.TaskProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 金柏宇
 * @version 1.0
 * @date 2020/3/26 22:26
 */
@Service
public class TaskInfoServiceImpl extends ServiceImpl<TaskInfoDao,ExamineTaskDetailVo> implements TaskInfoService {
    //注入两个Mapper用于查询任务对应的原料、半成品信息
    @Autowired
    private TaskMaterialMapper taskMaterialMapper;
    @Autowired
    private TaskProductMapper taskProductMapper;
    @Override
    public IPage<ExamineTaskDetailVo> getTaskByPagging(String productionNo, Page<ExamineTaskDetailVo> page) throws PaggingParamsException {
        //----------作废-----------------------------------------------------------------------
        // PaggingBean paggingBean=new PaggingBean(params,total);
        //分页查询出任务列表
        //List<TaskVo> taskList=taskInfoDao.getTaskByPagging(productionNo,paggingBean.getOffset(),paggingBean.getCorrectPageSize());
        //---------------------------------------------------------------------------------

        //检查参数是否正确
        if(page.getSize()<=0){
            throw new PaggingParamsException("参数异常");
        }
        LambdaQueryWrapper<ExamineTaskDetailVo> wrapper=new LambdaQueryWrapper<>();
        //根据生产计划编号查询任务详情（包括原料、半成品信息）
        wrapper.eq(ExamineTaskDetailVo::getProductionPlanNo,productionNo)
                //按预期开始时间排序
                .orderByAsc(ExamineTaskDetailVo::getForecastStartDate);
        IPage<ExamineTaskDetailVo> iPage=baseMapper.selectPage(page,wrapper);
        List<ExamineTaskDetailVo> taskList = iPage.getRecords();
        if(!taskList.isEmpty()) {
            taskList.parallelStream().forEach(taskDetailVo -> {
                //用任务编号查询对应原料和成品信息
                String taskNo = taskDetailVo.getTaskNo();
                List<TaskMaterialGoodsVo> taskMaterials = taskMaterialMapper.selectMaterialAndGoodsByTaskNo(taskNo);
                List<TaskProductGoodsVo> taskProducts = taskProductMapper.selectProductAndGoodsByTaskNo(taskNo);
                taskDetailVo.setTaskMaterialGoodsVos(taskMaterials);
                taskDetailVo.setTaskProductGoodsVos(taskProducts);
            });
        }
        return iPage;
    }

}