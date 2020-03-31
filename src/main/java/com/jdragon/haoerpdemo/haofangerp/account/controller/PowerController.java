package com.jdragon.haoerpdemo.haofangerp.account.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Power;
import com.jdragon.haoerpdemo.haofangerp.account.service.PowerService;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.ResultCode;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午4:20
 * @Description: 权限管理控制器
 */
@RestController
@RequestMapping("/power")
@Api(tags = "权限控制")
public class PowerController {

    @Autowired
    private PowerService powerService;

    /**
     * 分页获取权限列表
     * @return
     */
    @GetMapping("/list/{pageNo}/{pageSize}")
    @ApiOperation("获取权限列表")
    public Result listPowers(@ApiParam(name = "pageNo",value = "页码")@PathVariable("pageNo")int pageNo,
                             @ApiParam(name = "pageSize",value = "每页大小")@PathVariable("pageSize")int pageSize){
        return Result.success(Result.SUCCESS_MESSAGE).setResult(powerService.listPowers(new Page<>(pageNo,pageSize)));
    }

    /**
     * 根据角色id分页获取已赋予权限列表
     * @param pageNo 页数
     * @param pageSize 每一页的大小
     * @param roleId 角色id
     * @return
     */
    @GetMapping("/assigned/{pageNo}/{pageSize}")
    @ApiOperation("根据员工id分页获取已赋予角色列表")
    public Result getAssignedPowersByRoleId(@ApiParam(name = "pageNo",value = "页数")@PathVariable("pageNo")int pageNo, @ApiParam(name = "pageSize",value = "每页大小")@PathVariable("pageSize")int pageSize,
                                               @ApiParam(name = "roleId",value = "角色id")@RequestParam("roleId")int roleId){
        return Result.success(Result.SUCCESS_MESSAGE).setResult(powerService.getAssignedPowersByRoleId(pageNo, pageSize, roleId));
    }

    /**
     * 根据角色id分页获取未赋予权限列表(按权重从大到小排列)
     * @param pageNo 页数
     * @param pageSize 每一页的大小
     * @param roleId 角色id
     * @return
     */
    @GetMapping("/unAssigned/{pageNo}/{pageSize}")
    @ApiOperation("根据员工id分页获取未赋予角色列表")
    public Result getUnAssignedPowersByEmployeeId(@ApiParam(name = "pageNo",value = "页数")@PathVariable("pageNo")int pageNo, @ApiParam(name = "pageSize",value = "每页大小")@PathVariable("pageSize")int pageSize,
                                                 @ApiParam(name = "roleId",value = "角色id")@RequestParam("roleId")int roleId){
        return Result.success(Result.SUCCESS_MESSAGE).setResult(powerService.getUnAssignedPowersByRoleId(pageNo, pageSize, roleId));
    }

    /**
     * 添加角色权限
     * @param roleId 角色id
     * @param powerId 权限id
     * @return
     */
    @PostMapping("/addPowerOfRole")
    @ApiOperation("添加角色权限")
    public Result addPowerOfRole(@ApiParam(name = "roleId",value = "角色id")@RequestParam("roleId")int roleId,
                                    @ApiParam(name = "powerId",value = "权限id")@RequestParam("powerId")int powerId){
        try{
            return Result.success().setResult(powerService.addPowerOfRole(roleId, powerId));
        }catch (Exception e){
            return Result.error(ResultCode.SYSTEM_ERROR).setResult(e.getMessage());
        }
    }

    /**
     * 删除角色权限
     * @param roleId 角色id
     * @param powerId 权限id
     * @return
     */
    @DeleteMapping("/deletePowerOfRole")
    @ApiOperation("删除角色权限")
    public Result deletePowerOfRole(@ApiParam(name = "roleId",value = "角色id")@RequestParam("roleId")int roleId,
                                 @ApiParam(name = "powerId",value = "权限id")@RequestParam("powerId")int powerId){
        try{
            return Result.success().setResult(powerService.deletePowerOfRole(roleId, powerId));
        }catch (Exception e){
            return Result.error(ResultCode.SYSTEM_ERROR).setResult(e.getMessage());
        }
    }

}
