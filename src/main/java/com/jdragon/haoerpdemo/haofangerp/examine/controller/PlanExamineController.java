package com.jdragon.haoerpdemo.haofangerp.examine.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
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
public class  PlanExamineController {
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

    @GetMapping("/plans/{pageNum}/{pageSize}")
    @ApiOperation("分页形式获取所有未删除的计划列表")
    public Result getAllPlansToExamine(@ApiParam(value = "当前页码",defaultValue = "1") @PathVariable("pageNum") int pageNum,
                                       @ApiParam(value = "每页条数",defaultValue = "3") @PathVariable("pageSize") int pageSize){
        try {
            //  long total=planExamineService.totalCount();//获取总记录数
            IPage<Plan> iPage=planExamineService.getPlanByPagging(new Page<>(pageNum,pageSize));
            List<Plan> plans=iPage.getRecords();
            ResponseVo<Plan> responseVo=new ResponseVo<>();
            responseVo.setData(plans);
            if(plans.isEmpty()){
                responseVo.setTotal(0);
                return Result.error(responseVo);
            }else{
                responseVo.setTotal(iPage.getTotal());
                return Result.success(responseVo);
            }
        } catch(Exception e){
            return Result.error(e.getMessage());
        }
    }

}
