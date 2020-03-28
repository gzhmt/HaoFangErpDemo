package com.jdragon.haoerpdemo.haofangerp.account.controller;

import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Power;
import com.jdragon.haoerpdemo.haofangerp.account.service.PowerService;
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
@Api(tags = "角色控制")
public class PowerController {

    @Autowired
    private PowerService powerService;

    /**
     * 获取权限列表(按权重从大到小排列)
     * @return
     */
    @GetMapping("/listPowers")
    @ApiOperation("获取权限列表(按权重从大到小排列)")
    public Result listPowers(){
        List<Power> powers= powerService.listPowers();
        return Result.success(Result.SUCCESS_MESSAGE).setResult(powers);
    }

    /**
     * 根据角色id分页获取已赋予权限列表(按权重从大到小排列)
     * @param pageNo 页数
     * @param pageSize 每一页的大小
     * @param roleId 角色id
     * @return
     */
    @GetMapping("/assignedPowers/{pageNo}/{pageSize}/{roleId}")
    @ApiOperation("根据员工id分页获取已赋予角色列表")
    public Result getAssignedPowersByRoleId(@ApiParam(name = "pageNo",value = "页数")@PathVariable("pageNo")int pageNo, @ApiParam(name = "pageSize",value = "每页大小")@PathVariable("pageSize")int pageSize,
                                               @ApiParam(name = "roleId",value = "角色id")@PathVariable("roleId")int roleId){
        List<Power> powers= powerService.getAssignedPowersByRoleId(pageNo, pageSize, roleId);
        return Result.success(Result.SUCCESS_MESSAGE).setResult(powers);
    }

    /**
     * 根据角色id分页获取未赋予权限列表(按权重从大到小排列)
     * @param pageNo 页数
     * @param pageSize 每一页的大小
     * @param roleId 角色id
     * @return
     */
    @GetMapping("/unAssignedPowers/{pageNo}/{pageSize}/{roleId}")
    @ApiOperation("根据员工id分页获取未赋予角色列表")
    public Result getUnAssignedPowersByEmployeeId(@ApiParam(name = "pageNo",value = "页数")@PathVariable("pageNo")int pageNo, @ApiParam(name = "pageSize",value = "每页大小")@PathVariable("pageSize")int pageSize,
                                                 @ApiParam(name = "roleId",value = "角色id")@PathVariable("roleId")int roleId){
        List<Power> powers= powerService.getUnAssignedPowersByRoleId(pageNo, pageSize, roleId);
        return Result.success(Result.SUCCESS_MESSAGE).setResult(powers);
    }

    /**
     * 添加角色权限
     * @param roleId 角色id
     * @param powerId 权限id
     * @return
     */
    @PostMapping("/addPowerOfRole/{roleId}/{powerId}")
    @ApiOperation("添加角色权限")
    public Result addPowerOfRole(@ApiParam(name = "roleId",value = "角色id")@PathVariable("roleId")int roleId,
                                    @ApiParam(name = "powerId",value = "权限id")@PathVariable("powerId")int powerId){
        return powerService.addPowerOfRole(roleId, powerId);
    }

    /**
     * 删除角色权限
     * @param roleId 角色id
     * @param powerId 权限id
     * @return
     */
    @PostMapping("/deletePowerOfRole/{roleId}/{powerId}")
    @ApiOperation("删除角色权限")
    public Result deletePowerOfRole(@ApiParam(name = "roleId",value = "角色id")@PathVariable("roleId")int roleId,
                                 @ApiParam(name = "powerId",value = "权限id")@PathVariable("powerId")int powerId){
        return powerService.deletePowerOfRole(roleId, powerId);
    }

}
