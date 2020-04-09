package com.jdragon.haoerpdemo.haofangerp.account.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdragon.haoerpdemo.haofangerp.account.domain.vo.EmployeeVo;
import com.jdragon.haoerpdemo.haofangerp.account.service.EmployeeService;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午4:34
 * @Description: 用户接口
 */
@RestController
@RequestMapping("/employee")
@Api(tags = "员工接口")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    /**
     * 添加用户、用户自行注册。
     * @param employeeVo
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("注册")
    public Result register(@RequestBody EmployeeVo employeeVo) {
        try {
            return Result.success()
                    .setResult(employeeService.register(employeeVo));
        }catch (Exception e){
            return Result.error().setResult(e.getMessage());
        }
    }

    /**
     * 分页获取员工列表
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @return
     */
    @GetMapping("/list/{pageNo}/{pageSize}")
    @ApiOperation("分页获取角色列表")
    public Result listRoles(@ApiParam(name = "pageNo",value = "页码")@PathVariable("pageNo")int pageNo,
                            @ApiParam(name = "pageSize",value = "每页大小")@PathVariable("pageSize")int pageSize){
        return Result.success().setResult(employeeService.listEmployees(new Page<>(pageNo,pageSize)));
    }


}
