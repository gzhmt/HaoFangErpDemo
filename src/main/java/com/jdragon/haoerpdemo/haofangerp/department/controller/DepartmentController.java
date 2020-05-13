package com.jdragon.haoerpdemo.haofangerp.department.controller;

import com.jdragon.haoerpdemo.haofangerp.commons.constant.ResultCode;
import com.jdragon.haoerpdemo.haofangerp.commons.exceptions.HFException;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.department.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.05.12 12:34
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/department")
@Api(tags = "部门相关")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @GetMapping("/getAllOrganizational")
    @ApiOperation("获取组织机构图")
    public Result getAllOrganizational(){
        return Result.success(departmentService.getAllOrganizational());
    }


    @GetMapping("/getSupOrganizational")
    @ApiOperation("获取我的上级部门")
    public Result getSupOrganizational(){
        return Result.success(departmentService.getSupOrganizational());
    }

    @GetMapping("/getSubOrganizational")
    @ApiOperation("获取我下级部门")
    public Result getSubOrganizational(){
        return Result.success(departmentService.getSubOrganizational());
    }

    @DeleteMapping("/deleteDepartment")
    @ApiOperation("根据部门id删除部门")
    public Result deleteDepartment(@ApiParam(name = "departmentId",value = "部门id")@RequestParam int departmentId){
        try{
            if(departmentService.deleteDepartment(departmentId)){
                return Result.success().setResult("删除成功");
            }
            return Result.unKnowError("删除失败");
        }catch (HFException e){
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/mergeDepartment")
    @ApiOperation("根据部门id来合并两个部门")
    public Result mergeDepartment(@ApiParam(name = "mergedDepartmentId",value = "被合并的部门id")@RequestParam int mergedDepartmentId,
                                  @ApiParam(name = "mergingDepartmentId",value = "扩大的部门id")@RequestParam int mergingDepartmentId){
        try{
            if(departmentService.mergeDepartment(mergedDepartmentId,mergingDepartmentId)){
                return Result.success("合并成功");
            }
            return Result.unKnowError("合并失败");
        }catch (HFException e){
            return Result.error(e.getMessage());
        }
    }
}
