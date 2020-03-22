package com.jdragon.haoerpdemo.haofangerp.production.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.PlanVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import com.jdragon.haoerpdemo.haofangerp.production.service.PlanService;
import com.jdragon.haoerpdemo.haofangerp.security.commons.SecurityContextHolderHelper;
import io.swagger.annotations.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


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

    @GetMapping("/id/{id}")
    @ApiOperation("根据计划号获取计划")
    public Result byId(@PathVariable Long id){
        return Result.success().setResult(planService.getById(id));
    }

    @GetMapping("/list/{page}")
    @ApiOperation("获取生产计划列表")
    public Result list(@PathVariable int page){
        try{
            IPage<Plan> planList = planService.list(new Page<>(page,20));
            return Result.success().setResult(planList);
        }catch (Exception e){
            return Result.error().setResult(e.getMessage());
        }
    }

    @PostMapping("/create")
    @ApiOperation(value = "创建生产计划")
    public Result create(@RequestBody PlanVo planVo) {
        try{
            Plan plan = planService.save(planVo);
            return Result.success().setResult(plan);
        }catch (Exception e){
            return Result.error().setResult(e.getMessage());
        }
    }

    @PostMapping("/update")
    @ApiOperation("更改生产计划")
    public Result update(@RequestBody PlanVo planVo){
        try {
            return Result.success().setResult(planService.update(planVo));
        }catch (Exception e){
            return Result.error().setResult(e.getMessage());
        }
    }

    @PostMapping("/delete")
    @ApiOperation("删除生产计划")
    public Result delete(@RequestBody String productionNo){
        try {
            return Result.success().setResult(planService.delete(productionNo));
        } catch (Exception e) {
            return Result.error().setResult(e.getMessage());
        }
    }
}
