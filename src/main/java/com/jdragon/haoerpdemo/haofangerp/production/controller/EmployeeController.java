package com.jdragon.haoerpdemo.haofangerp.production.controller;

import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.EmployeeVo;
import com.jdragon.haoerpdemo.haofangerp.production.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.20 16:48
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
