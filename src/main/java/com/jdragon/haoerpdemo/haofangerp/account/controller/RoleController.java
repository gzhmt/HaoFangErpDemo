package com.jdragon.haoerpdemo.haofangerp.account.controller;

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
     * 获取角色列表(按权重从大到小排列)
     * @return
     */
    @GetMapping("/listRoles")
    @ApiOperation("获取角色列表(按权重从大到小排列)")
    public Result ListRoles(){
        List<Role> roles= roleService.listRoles();
        return Result.success(Result.SUCCESS_MESSAGE).setResult(roles);
    }

    /**
     * 添加角色
     * @param roleVo 角色vo类
     * @param bindingResult
     * @return
     */
    @PostMapping("/addRole")
    @ApiOperation("添加角色")
    public Result addRole(@ApiParam(name = "roleVo",value = "角色vo类")@RequestBody @Validated RoleVo roleVo, BindingResult bindingResult){
        Map<String, String> map = ValidationUtils.checkBindingResult(bindingResult);
        if (map != null) {
            return Result.error("请求参数非法");
        }
        return roleService.addRole(roleVo);
    }

    /**
     * 根据角色id删除角色
     * @param roleId 角色id
     * @return
     */
    @PostMapping("/deleteRole/{roleId}")
    @ApiOperation("根据角色id删除角色")
    public Result deleteRole(@ApiParam(name = "roleId",value = "角色id")@PathVariable("roleId")int roleId){
        return roleService.deleteRole(roleId);
    }

    /**
     * 根据员工id分页获取已赋予角色列表(按权重从大到小排列)
     * @param pageNo 页数
     * @param pageSize 每一页的大小
     * @param employeeId 员工id
     * @return
     */
    @GetMapping("/assignedRoles/{pageNo}/{pageSize}/{employeeId}")
    @ApiOperation("根据员工id分页获取已赋予角色列表")
    public Result getAssignedRolesByEmployeeId(@ApiParam(name = "pageNo",value = "页数")@PathVariable("pageNo")int pageNo, @ApiParam(name = "pageSize",value = "每页大小")@PathVariable("pageSize")int pageSize,
                           @ApiParam(name = "employeeId",value = "员工id")@PathVariable("employeeId")int employeeId){
        List<Role> roles= roleService.getAssignedRolesByEmployeeId(pageNo, pageSize, employeeId);
        return Result.success(Result.SUCCESS_MESSAGE).setResult(roles);
    }


    /**
     * 根据员工id分页获取未赋予角色列表(按权重从大到小排列)
     * @param pageNo 页数
     * @param pageSize 每一页的大小
     * @param employeeId 员工id
     * @return
     */
    @GetMapping("/unAssignedRoles/{pageNo}/{pageSize}/{employeeId}")
    @ApiOperation("根据员工id分页获取未赋予角色列表")
    public Result getUnAssignedRolesByEmployeeId(@ApiParam(name = "pageNo",value = "页数")@PathVariable("pageNo")int pageNo, @ApiParam(name = "pageSize",value = "每页大小")@PathVariable("pageSize")int pageSize,
                           @ApiParam(name = "employeeId",value = "员工id")@PathVariable("employeeId")int employeeId){
        List<Role> roles= roleService.getUnAssignedRolesByEmployeeId(pageNo, pageSize, employeeId);
        return Result.success(Result.SUCCESS_MESSAGE).setResult(roles);
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
        return roleService.addRoleOfEmployee(employeeId, roleId);
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
        return roleService.deleteRoleOfEmployee(employeeId, roleId);
    }







}
