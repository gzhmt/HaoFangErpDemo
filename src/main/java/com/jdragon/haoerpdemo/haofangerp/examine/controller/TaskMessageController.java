package com.jdragon.haoerpdemo.haofangerp.examine.controller;

import com.jdragon.haoerpdemo.haofangerp.examine.service.TaskMessageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务相关信息控制器
 *      提供获取指定计划的任务列表数据接口
 *
 * @author 金柏宇
 * @version 1.0
 * @date 2020/3/26 22:05
 */
@RestController
@RequestMapping("/taskMessage")
@Api(tags = "计划审核任务数据请求接口")
public class TaskMessageController {
    @Autowired
    private TaskMessageService taskMessageService;

}
