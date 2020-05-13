package com.jdragon.haoerpdemo.haofangerp.production.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.ResultCode;
import com.jdragon.haoerpdemo.haofangerp.commons.exceptions.HFException;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.TaskVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Task;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.task.*;
import com.jdragon.haoerpdemo.haofangerp.production.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;


/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 17:17
 * @Description: 生产任务相关接口
 */
@RestController
@RequestMapping("task")
@Api(tags = "生产任务相关")
@Slf4j
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("/{taskNo}")
    @ApiOperation("根据任务单号获取生产任务详情")
    public Result byId(@ApiParam("计划单号")@PathVariable String taskNo){
        try {
            return Result.success(taskService.queryTaskDetail(taskNo));
        } catch (UnknownError e){
            return Result.unKnowError(e.getMessage());
        } catch (HFException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("list/{page}/{size}")
    @ApiOperation("根据生产计划单号获取生产任务列表")
    public Result getList(@ApiParam(value = "页码", defaultValue="1")@PathVariable  Integer page,
                          @ApiParam(value = "页面大小", defaultValue="20")@PathVariable  Integer size,
                          @ApiParam(value = "生产计划单号", defaultValue="SC-20200323-0001") @RequestParam String planNo){
        return Result.success(taskService.list(new Page<>(page,size),planNo));
    }

    @PostMapping("/create")
    @ApiOperation("创建生产任务")
    public Result create( @Valid @RequestBody TaskInsertVo taskInsertVo){
        try{
            return Result.success(taskService.save(taskInsertVo));
        } catch (UnknownError e){
            return Result.unKnowError(e.getMessage());
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

//    @PutMapping("/task")
//    @ApiOperation("修改生产任务")
//    public Result update(@ApiParam("生产任务") @RequestBody @Valid TaskUpdateVo taskUpdateVo){
//        try {
//            return Result.success().setResult(taskService.update(taskUpdateVo));
//        } catch (Exception e) {
//            return Result.error().setResult(e.getMessage());
//        }
//    }

    @DeleteMapping("/delete")
    @ApiOperation("根据任意个生产单号删除生产任务")
    public Result delete(@ApiParam(value = "生产单号")@RequestBody String[] taskNo){
        try {
            return Result.success(taskService.delete(taskNo));
        } catch (UnknownError e){
            return Result.unKnowError(e.getMessage());
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    //启动多个任务
    //撤销多个任务

}
