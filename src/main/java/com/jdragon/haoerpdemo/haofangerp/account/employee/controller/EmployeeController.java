package com.jdragon.haoerpdemo.haofangerp.account.employee.controller;

import com.jdragon.haoerpdemo.haofangerp.account.employee.domain.vo.EmployeeVo;
import com.jdragon.haoerpdemo.haofangerp.account.employee.service.EmployeeService;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            return Result.success("注册成功")
                    .setResult(employeeService.register(employeeVo));
        }catch (Exception e){
            return Result.success(e.getMessage());
        }
    }
}
