package com.jdragon.haoerpdemo.haofangerp.production.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.PlanVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import com.jdragon.haoerpdemo.haofangerp.production.service.PlanService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 17:16
 * @Description: 生产计划相关接口
 */
@Slf4j
@RestController
@RequestMapping("plan")
@Api(tags = "生产计划相关")
public class PlanController {

    @Autowired
    PlanService planService;

    @GetMapping("/productionNo/{productionNo}")
    @ApiOperation("根据计划单号获取计划")
    public Result byId(@ApiParam(name = "productionNo",value = "计划单号")@PathVariable String productionNo){
        try {
            return Result.success().setResult(planService.getByProductionNo(productionNo));
        }catch (Exception e){
            return Result.error().setResult(e.getMessage());
        }
    }

    @GetMapping("/list/{page}")
    @ApiOperation("获取生产计划列表")
    public Result list(@ApiParam(name = "page",value = "页数")@PathVariable int page){
        try{
            IPage<Plan> planList = planService.list(new Page<>(page,20));
            return Result.success().setResult(planList);
        }catch (Exception e){
            return Result.error().setResult(e.getMessage());
        }
    }

    @PostMapping("/create")
    @ApiOperation(value = "创建生产计划")
    public Result create(@ApiParam(name = "planVo",value = "计划具体参数")@RequestBody PlanVo planVo) {
        try{
            Plan plan = planService.save(planVo);
            return Result.success().setResult(plan);
        }catch (Exception e){
            return Result.error().setResult(e.getMessage());
        }
    }

    @PutMapping("/update")
    @ApiOperation("更改生产计划")
    public Result update(@ApiParam(name = "planVo",value = "计划具体参数")@RequestBody PlanVo planVo){
        try {
            return Result.success().setResult(planService.update(planVo));
        }catch (Exception e){
            return Result.error().setResult(e.getMessage());
        }
    }


    @DeleteMapping("/delete/{productionNo}")
    @ApiOperation("删除生产计划")
    public Result delete(@ApiParam(name = "productionNo",value = "计划生产单号") @PathVariable String productionNo){
        try {
            return Result.success().setResult(planService.delete(productionNo));
        } catch (Exception e) {
            return Result.error().setResult(e.getMessage());
        }
    }
}
