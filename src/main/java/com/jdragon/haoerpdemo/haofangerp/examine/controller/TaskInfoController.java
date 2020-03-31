package com.jdragon.haoerpdemo.haofangerp.examine.controller;

import com.jdragon.haoerpdemo.haofangerp.commons.constant.ResultCode;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.examine.component.PaggingParams;
import com.jdragon.haoerpdemo.haofangerp.examine.component.exceptions.PaggingParamsException;
import com.jdragon.haoerpdemo.haofangerp.examine.domain.vo.ResponseVo;
import com.jdragon.haoerpdemo.haofangerp.examine.service.TaskInfoService;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.TaskVo;
import io.swagger.annotations.Api;
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

    @GetMapping("/all/{productionNo}")
    public Result getAllTaskInfo(@PathVariable("productionNo") String productionNo, @RequestBody PaggingParams params){
        //暂不使用redis缓存
        //查询总数
        long total=taskInfoService.getTaskToal();
        try {
            List<TaskVo> taskVoList = taskInfoService.getTaskByPagging(productionNo, params, total);
            ResponseVo responseVo=new ResponseVo();
            responseVo.setTotal(total);
            responseVo.setData(taskVoList);
            if(taskVoList.isEmpty()) {
                responseVo.setTotal(0);
                return Result.error().setResult(responseVo);
            }else{
                return Result.success().setResult(responseVo);
            }
        }catch(PaggingParamsException e){
            return Result.error(ResultCode.SYSTEM_ERROR).setResult(e.getMessage());
        }catch(Exception e){
            return Result.error(e.getMessage());
        }
    }

}
