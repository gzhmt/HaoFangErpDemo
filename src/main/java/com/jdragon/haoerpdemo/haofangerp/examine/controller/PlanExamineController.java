package com.jdragon.haoerpdemo.haofangerp.examine.controller;

import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.examine.component.PaggingParams;
import com.jdragon.haoerpdemo.haofangerp.examine.component.exceptions.PaggingParamsException;
import com.jdragon.haoerpdemo.haofangerp.examine.service.PlanExamineService;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 计划审核控制器
 *      提供计划审核请求接口
 *      提供数据请求接口
 * @author 金柏宇
 * @version 1.0
 * @date 2020/3/26 21:54
 */

@RestController
@RequestMapping("/examine")
@Api(tags = "计划审核接口")
public class PlanExamineController {
    @Autowired
    private PlanExamineService planExamineService;
    @PatchMapping("/{productionNo}/{examineCode}")
    @ApiOperation("计划审核结果提交")
    @ApiImplicitParams({
            @ApiImplicitParam(name="productionNo",value = "生产单号",required = true,dataType="String"),
            @ApiImplicitParam(name="examineCode",value = "审批结果,整型，传5表示审核通过，传2表示驳回",required = true,dataType="int")}
    )

    public Result passExamine(@PathVariable("productionNo") String productionNo,@PathVariable("examineCode") int examineCode){
        return planExamineService.updateState(productionNo,examineCode);
    }

    @GetMapping("/plans")
    @ApiOperation("分页形式获取所有未删除的计划列表")
    @ApiImplicitParam(name="params",value = "分页所需参数",required = true,dataType="PaggingParams")
    public Result getAllPlansToExamine(@RequestBody PaggingParams params){
        try {
            long total=planExamineService.totalCount();//获取总记录数
            List<Plan> plans=planExamineService.getPlanByPagging(params,total);
            if(plans.isEmpty()){
                return Result.success("无数据");
            }else{
                return Result.success("获取数据成功").setResult(plans);
            }
        }catch(PaggingParamsException e){
            return Result.error("分页参数错误");
        }
    }

}
