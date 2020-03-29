package com.jdragon.haoerpdemo.haofangerp.account.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Role;
import com.jdragon.haoerpdemo.haofangerp.account.domain.vo.RoleVo;
import com.jdragon.haoerpdemo.haofangerp.account.service.RoleService;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.commons.tools.ValidationUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午3:54
 * @Description: 角色管理控制器
 */
@RestController
@Validated
@RequestMapping("/role")
@Api(tags = "权限控制")
public class RoleController {

    @Autowired
    RoleService roleService;

    /**
     * 分页获取角色列表
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @return
     */
    @GetMapping("/list/{pageNo}/{pageSize}")
    @ApiOperation("分页获取角色列表")
    public Result listRoles(@ApiParam(name = "pageNo",value = "页码")@PathVariable("pageNo")int pageNo,
                            @ApiParam(name = "pageSize",value = "每页大小")@PathVariable("pageSize")int pageSize){
        return Result.success(Result.SUCCESS_MESSAGE).setResult(roleService.listRoles(new Page<>(pageNo,pageSize)));
    }

    /**
     * 添加角色
     * @param roleVo 角色vo类
     * @param bindingResult 校验参数接口
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("添加角色")
    public Result addRole(@ApiParam(name = "roleVo",value = "角色vo类")@RequestBody @Validated RoleVo roleVo, BindingResult bindingResult){
        Map<String, String> map = ValidationUtils.checkBindingResult(bindingResult);
        if (Optional.ofNullable(map).isPresent()) {
            return Result.error("请求参数非法");
        }
        try{
            return Result.success().setResult(roleService.addRole(roleVo));
        }catch (Exception e){
            return Result.error().setResult(e.getMessage());
        }
    }

    /**
     * 根据角色id删除角色
     * @param roleId 角色id
     * @return
     */
    @PostMapping("/delete/{roleId}")
    @ApiOperation("根据角色id删除角色")
    public Result deleteRole(@ApiParam(name = "roleId",value = "角色id")@PathVariable("roleId")int roleId){
        try{
            return Result.success().setResult(roleService.deleteRole(roleId));
        }catch (Exception e){
            return Result.error().setResult(e.getMessage());
        }
    }

    /**
     * 根据员工id分页获取已赋予角色列表
     * @param pageNo 页数
     * @param pageSize 每一页的大小
     * @param employeeId 员工id
     * @return
     */
    @GetMapping("/assigned/{pageNo}/{pageSize}/{employeeId}")
    @ApiOperation("根据员工id分页获取已赋予角色列表")
    public Result getAssignedRolesByEmployeeId(@ApiParam(name = "pageNo",value = "页数")@PathVariable("pageNo")int pageNo, @ApiParam(name = "pageSize",value = "每页大小")@PathVariable("pageSize")int pageSize,
                           @ApiParam(name = "employeeId",value = "员工id")@PathVariable("employeeId")int employeeId){
        return Result.success(Result.SUCCESS_MESSAGE).setResult(roleService.getAssignedRolesByEmployeeId(pageNo, pageSize, employeeId));
    }


    /**
     * 根据员工id分页获取未赋予角色列表
     * @param pageNo 页数
     * @param pageSize 每一页的大小
     * @param employeeId 员工id
     * @return
     */
    @GetMapping("/unAssigned/{pageNo}/{pageSize}/{employeeId}")
    @ApiOperation("根据员工id分页获取未赋予角色列表")
    public Result getUnAssignedRolesByEmployeeId(@ApiParam(name = "pageNo",value = "页数")@PathVariable("pageNo")int pageNo, @ApiParam(name = "pageSize",value = "每页大小")@PathVariable("pageSize")int pageSize,
                           @ApiParam(name = "employeeId",value = "员工id")@PathVariable("employeeId")int employeeId){
        return Result.success(Result.SUCCESS_MESSAGE).setResult(roleService.getUnAssignedRolesByEmployeeId(pageNo, pageSize, employeeId));
    }

    /**
     * 添加员工角色
     * @param employeeId 员工id
     * @param roleId 角色id
     * @return
     */
    @PostMapping("/addRoleOfEmployee/{employeeId}/{roleId}")
    @ApiOperation("添加员工角色")
    public Result addRoleOfEmployee(@ApiParam(name = "employeeId",value = "员工id")@PathVariable("employeeId")int employeeId,
                                       @ApiParam(name = "roleId",value = "角色id")@PathVariable("roleId")int roleId){
        try{
            return Result.success().setResult(roleService.addRoleOfEmployee(employeeId, roleId));
        }catch (Exception e){
            return Result.error().setResult(e.getMessage());
        }
    }

    /**
     * 删除员工角色
     * @param employeeId 员工id
     * @param roleId 角色id
     * @return
     */
    @PostMapping("/deleteRoleOfEmployee/{employeeId}/{roleId}")
    @ApiOperation("删除员工角色")
    public Result deleteRoleOfEmployee(@ApiParam(name = "employeeId",value = "员工id")@PathVariable("employeeId")int employeeId,
                                    @ApiParam(name = "roleId",value = "角色id")@PathVariable("roleId")int roleId){
        try{
            return Result.success().setResult(roleService.deleteRoleOfEmployee(employeeId, roleId));
        }catch (Exception e){
            return Result.error().setResult(e.getMessage());
        }
    }
}
