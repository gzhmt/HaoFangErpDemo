package com.jdragon.haoerpdemo.haofangerp.examine.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.ResultCode;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.examine.component.PaggingParams;
import com.jdragon.haoerpdemo.haofangerp.examine.component.exceptions.PaggingParamsException;
import com.jdragon.haoerpdemo.haofangerp.examine.domain.vo.ExamineTaskDetailVo;
import com.jdragon.haoerpdemo.haofangerp.examine.domain.vo.ResponseVo;
import com.jdragon.haoerpdemo.haofangerp.examine.service.TaskInfoService;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.TaskVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.task.TaskDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 任务相关信息控制器
 *      提供获取指定计划的任务列表数据接口
 *
 * @author 金柏宇
 * @version 1.0
 * @date 2020/3/26 22:05
 */
@RestController
@RequestMapping("/taskInfo")
@Api(tags = "计划审核任务数据请求接口")
public class TaskInfoController {
    @Autowired
    private TaskInfoService taskInfoService;

    @GetMapping("/all/{productionNo}/{pageNum}/{pageSize}")
    public Result getAllTaskInfo(@ApiParam(value = "生产计划编号",defaultValue = "SC-20200323-0001") @PathVariable("productionNo") String productionNo,
                                 @ApiParam(value = "当前页码",defaultValue ="1") @PathVariable("pageNum") long pageNum,
                                 @ApiParam(value = "每页条数",defaultValue ="3")  @PathVariable("pageSize") long pageSize){
        //暂不使用redis缓存
        //查询总数
        // long total=taskInfoService.getTaskToal();
        try {
            IPage<ExamineTaskDetailVo> iPage = taskInfoService.getTaskByPagging(productionNo,new Page<>(pageNum,pageSize));
            List<ExamineTaskDetailVo> taskVoList = iPage.getRecords();
            ResponseVo responseVo=new ResponseVo();
            //responseVo.setTotal(total);
            responseVo.setData(taskVoList);
            if(taskVoList.isEmpty()) {//此时responseVo.total默认为0
                return Result.error().setResult(responseVo);
            }else{
                responseVo.setTotal(iPage.getTotal());
                return Result.success().setResult(responseVo);
            }
        }catch(PaggingParamsException e){
            return Result.error().setResult(e.getMessage());
        }catch(Exception e){
            return Result.error().setResult(e.getMessage());
        }
    }

}
