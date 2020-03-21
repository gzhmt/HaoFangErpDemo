package com.jdragon.haoerpdemo.haofangerp.production.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.PlanVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import com.jdragon.haoerpdemo.haofangerp.production.service.PlanService;
import io.swagger.annotations.*;
import org.assertj.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.Cacheable;

import java.util.Optional;


/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 17:16
 * @Description: 生产计划相关接口
 */

@RestController
@RequestMapping("plan")
@Api(tags = "生产计划相关")
public class PlanController {

    @Autowired
    PlanService planService;

    @GetMapping("/{id}")
    @ApiOperation("根据计划号获取计划")
    public Result byId(@PathVariable int id){
        return Result.success(Result.SUCCESS_MESSAGE).setResult(planService.getById(id));
    }

    @GetMapping("/list/{page}")
    @ApiOperation("获取生产计划列表")
    public Result list(@PathVariable int page){
        IPage<Plan> planList = planService.list(new Page<>(page,20));
        return Result.success(Result.SUCCESS_MESSAGE).setResult(planList);
    }

    @PostMapping("/create")
    @ApiOperation(value = "创建生产计划",response = PlanVo.class)
    public Result create(@RequestBody PlanVo planVo){
        planVo.setCreateDate(DateUtil.now());
        return Result.success(Optional.ofNullable(planService.save(planVo)).isPresent()?"创建成功":"创建失败");
    }

    @PostMapping("/update")
    @ApiOperation("更改生产计划")
    public Result update(@RequestBody PlanVo planVo){
        return Result.success(planService.update(planVo)?"更改成功":"更改失败");
    }

    @PostMapping("/delete")
    @ApiOperation("删除生产计划")
    public Result delete(@RequestBody Long planId){
        return Result.success(planService.delete(planId)?"删除成功":"删除失败");
    }
}
