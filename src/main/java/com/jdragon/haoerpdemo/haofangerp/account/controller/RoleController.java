package com.jdragon.haoerpdemo.haofangerp.account.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Role;
import com.jdragon.haoerpdemo.haofangerp.account.domain.vo.EmployeeRoleVo;
import com.jdragon.haoerpdemo.haofangerp.account.service.RoleService;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

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
     * @param pageNo 页数
     * @param pageSize 每一页的大小
     * @return
     */
    @GetMapping("/listRole/{pageNo}/{pageSize}")
    @ApiOperation("分页获取角色列表")
    public Result listRole(@ApiParam(name = "pageNo",value = "页数")@PathVariable("pageNo")int pageNo, @ApiParam(name = "pageSize",value = "每页大小")@PathVariable("pageSize")int pageSize){
        IPage<Role> roleList = roleService.listRole(new Page<>(pageNo,pageSize));
        return Result.success(Result.SUCCESS_MESSAGE).setResult(roleList);
    }


    /**
     * 分页获取员工角色关系列表
     * @param pageNo 页数
     * @param pageSize 每一页的大小
     * @return
     */
    @GetMapping("/listEmployeeRole/{pageNo}/{pageSize}")
    @ApiOperation("分页获取员工角色关系列表")
    public Result listAllEmployeeRole(@ApiParam(name = "pageNo",value = "页数")@PathVariable("pageNo")int pageNo, @ApiParam(name = "pageSize",value = "每页大小")@PathVariable("pageSize")int pageSize){
        List<EmployeeRoleVo> employeeRoleVos= roleService.listEmployeeRole(pageNo,pageSize);
        return Result.success(Result.SUCCESS_MESSAGE).setResult(employeeRoleVos);
    }

    /**
     * 根据工号查询员工角色列表
     * @param employeeNo 工号
     * @return
     */
    @PostMapping("/getRolesByEmployeeNo")
    @ApiOperation("根据工号查询员工角色列表")
    public Result getRolesByEmployeeNo(@ApiParam(name = "employeeNo",value = "工号")@NotEmpty(message = "请求参数不能为空")String employeeNo){
        List<EmployeeRoleVo> employeeRoleVos= roleService.getRolesByEmployeeNo(employeeNo);
        if(employeeRoleVos.size() != 0){
            return Result.success(Result.SUCCESS_MESSAGE).setResult(employeeRoleVos);
        }
        return Result.error("查询用户角色失败");
    }

    /**
     * 员工角色修改
     * @param employeeRoleId 员工角色id
     * @param updateRoleId 更新角色id
     * @return
     */
    @PutMapping("/{employeeRoleId}/{updateRoleId}")
    @ApiOperation("员工角色修改")
    public Result updateRoleOfEmployee(@ApiParam(name = "employeeRoleId",value = "员工角色id")@PathVariable("employeeRoleId")int employeeRoleId, @ApiParam(name = "updateRoleId",value = "更新角色id")@PathVariable("updateRoleId")int updateRoleId){
        if(roleService.updateRoleOfEmployee(employeeRoleId, updateRoleId)){
            return Result.success(Result.SUCCESS_MESSAGE).setResult("员工角色修改成功");
        }
        return Result.error("员工角色修改失败");
    }
}
