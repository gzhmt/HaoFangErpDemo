package com.jdragon.haoerpdemo.haofangerp.department.controller;

import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.department.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/getOrganizational")
    @ApiOperation("获取组织机构图")
    public Result byId(){
        return Result.success().setResult(departmentService.getOrganizational());
    }
}
