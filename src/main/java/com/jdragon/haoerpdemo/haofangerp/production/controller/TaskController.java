package com.jdragon.haoerpdemo.haofangerp.production.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.TaskVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Task;
import com.jdragon.haoerpdemo.haofangerp.production.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("taskNo/{taskNo}")
    @ApiOperation("根据单号获取生产任务详情")
    public Result byId(@ApiParam("计划单号")@PathVariable String taskNo){
        try {
            return Result.success().setResult(taskService.queryTaskDetail(taskNo));
        } catch (Exception e) {
            return Result.error().setResult(e.getMessage());
        }
    }

    @GetMapping("list/{page}")
    @ApiOperation("获取生产任务列表")
    public Result getList(@ApiParam("计划单号")@PathVariable int page){
        try {
            IPage<Task> taskIPage = taskService.list(new Page<>(page,20));
            return Result.success().setResult(taskIPage);
        } catch (Exception e){
            return Result.error().setResult(e.getMessage());
        }
    }

    @PostMapping("/create")
    @ApiOperation("创建生产任务")
    public Result create(@RequestBody TaskVo taskVo){
        try{
            Task task = taskService.save(taskVo);
            return Result.success().setResult(task);
        }catch (Exception e){
            return Result.error().setResult(e.getMessage());
        }
    }

    @PutMapping("/update")
    @ApiOperation("更改生产任务")
    public Result update(@RequestBody TaskVo taskVo){
        try {
            return Result.success().setResult(taskService.update(taskVo));
        } catch (Exception e) {
            return Result.error().setResult(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{taskNo}")
    @ApiOperation("根据生产单号删除生产任务")
    public Result update(@ApiParam("生产单号")@PathVariable @RequestBody Long taskId){
        try {
            return Result.success().setResult(taskService.delete(taskId));
        } catch (Exception e) {
            return Result.error().setResult(e.getMessage());
        }
    }
    @PutMapping("/state")
    @ApiOperation("修改任务状态")
    public Result state(){
        try {
            return
        } catch (Exception e) {
            return Result.error().setResult(e.getMessage());
        }
    }
}
