package com.jdragon.haoerpdemo.haofangerp.examine.controller;

import com.jdragon.haoerpdemo.haofangerp.commons.constant.ResultCode;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.examine.component.PaggingParams;
import com.jdragon.haoerpdemo.haofangerp.examine.component.exceptions.PaggingParamsException;
import com.jdragon.haoerpdemo.haofangerp.examine.domain.vo.ResponseVo;
import com.jdragon.haoerpdemo.haofangerp.examine.service.PlanExamineService;
import com.jdragon.haoerpdemo.haofangerp.production.constant.PlanAuditStatusEnum;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 计划审核控制器
 *      提供计划审核请求接口
 *      提供数据请求接口
 *
 * @author 金柏宇
 * @version 1.0
 * @date 2020/3/26 21:54
 */

@RestController
@RequestMapping("/examine")
@Api(tags = "计划审核接口")
public class    PlanExamineController {
    @Autowired
    private PlanExamineService planExamineService;
    //将PATCH改为POST才能正常运行此URL
    @PostMapping
    @ApiOperation("计划审核结果提交")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name="productionNo",value = "生产单号",required = true,dataType="String"),
//            @ApiImplicitParam(name="examineCode",value = "审批结果,整型，传5表示审核通过，传2表示驳回",required = true,dataType="int")}
//    )

    public Result passExamine(@ApiParam(name = "productionNo",value = "计划生产单号")@RequestParam String productionNo,
                              @ApiParam(name = "planAuditStatusEnum",value = "审核后状态")@RequestParam PlanAuditStatusEnum planAuditStatusEnum){
        return planExamineService.updateState(productionNo,planAuditStatusEnum.getCode());
    }

    @GetMapping("/plans")
    @ApiOperation("分页形式获取所有未删除的计划列表")
    @ApiImplicitParam(name="params",value = "分页所需参数",required = true,dataType="PaggingParams")
    public Result getAllPlansToExamine(@RequestBody @NotNull PaggingParams params){
        try {
            long total=planExamineService.totalCount();//获取总记录数
            List<Plan> plans=planExamineService.getPlanByPagging(params,total);
            ResponseVo<Plan> responseVo=new ResponseVo<>();
            responseVo.setData(plans);
            if(plans.isEmpty()){
                responseVo.setTotal(0);
                return Result.error().setResult(responseVo);
            }else{
                responseVo.setTotal(total);
                return Result.success().setResult(responseVo);
            }
        }catch(PaggingParamsException e){
            return Result.error(ResultCode.SYSTEM_ERROR).setResult(e.getMessage());
        }catch(Exception e){
            return Result.error(e.getMessage());
        }
    }

}
