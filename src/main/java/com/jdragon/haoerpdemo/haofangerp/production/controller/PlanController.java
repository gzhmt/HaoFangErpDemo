package com.jdragon.haoerpdemo.haofangerp.production.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.ResultCode;
import com.jdragon.haoerpdemo.haofangerp.commons.exceptions.HFException;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.PlanVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import com.jdragon.haoerpdemo.haofangerp.production.service.PlanService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 17:16
 * @Description: 生产计划相关接口
 */
@Slf4j
@RestController
@RequestMapping("/plan")
@Api(tags = "生产计划相关")
public class PlanController {
    @Autowired
    PlanService planService;

    @GetMapping("/{productionNo}")
    @ApiOperation("根据计划单号获取计划")
    public Result byId(@ApiParam(name = "productionNo",value = "计划单号")@PathVariable String productionNo){
        try {
            return Result.success().setResult(planService.getByProductionNo(productionNo));
        }catch (Exception e){
            return Result.error().setResult(e.getMessage());
        }
    }

    @GetMapping("/list/{page}/{pageSize}")
    @ApiOperation("获取生产计划列表")
    public Result list(@ApiParam(name = "page", value = "页数") @PathVariable int page,
                       @ApiParam(name = "pageSize", value = "页大小")@PathVariable int pageSize,
                       @ApiParam(name = "state", value = "状态") @RequestParam(required = false) String state){
        IPage<Plan> planList = planService.list(new Page<>(page,pageSize),state);
        return Result.success().setResult(planList);
    }

    @PostMapping("/create")
    @ApiOperation(value = "创建生产计划")
    public Result create(@ApiParam(name = "planVo",value = "计划具体参数")@RequestBody @Valid PlanVo planVo) {
        try{
            Plan plan = planService.save(planVo);
            return Result.success().setResult(plan);
        }catch (UnknownError e){
            return Result.error(ResultCode.SYSTEM_UN_KNOW_ERROR).setResult(e.getMessage());
        } catch (HFException e) {
            return Result.error().setResult(e.getMessage());
        }
    }

    @PostMapping("/copy")
    @ApiOperation("复制生产计划")
    public Result update(@ApiParam(name = "productionNoList",value = "计划单号")@RequestBody String[] productionNoList){
        return Result.success().setResult(planService.copyPlans(productionNoList));
    }
    @DeleteMapping("/delete")
    @ApiOperation("删除生产计划")
    public Result delete(@ApiParam(name = "productionNoList",value = "计划生产单号") @RequestBody String[] productionNoList){
        return Result.success().setResult(planService.deletePlans(productionNoList));
    }

    @PutMapping("/update")
    @ApiOperation("更新生产计划")
    public Result update(@ApiParam(name = "productionNo",value = "计划生产单号")@RequestParam String productionNo,
                         @ApiParam(name = "planVo",value = "计划具体参数")@RequestBody @Valid PlanVo planVo){
        try {
            return Result.success().setResult(planService.update(productionNo,planVo));
        }catch (UnknownError e){
            return Result.error(ResultCode.SYSTEM_UN_KNOW_ERROR).setResult(e.getMessage());
        }catch (Exception e) {
            return Result.error().setResult(e.getMessage());
        }
    }

    @GetMapping("/planNameFuzzyQuery")
    @ApiOperation("模糊查询计划号")
    public Result planNameFuzzyQuery(@ApiParam(name = "productionNo",value = "计划生产单号")@RequestParam(required = false) String productionNo){
        return Result.success().setResult(planService.getFuzzyPlanName(productionNo));
    }
}
