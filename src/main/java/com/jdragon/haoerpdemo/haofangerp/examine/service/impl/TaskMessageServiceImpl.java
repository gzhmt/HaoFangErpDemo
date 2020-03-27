package com.jdragon.haoerpdemo.haofangerp.examine.service.impl;

import com.jdragon.haoerpdemo.haofangerp.examine.dao.TaskMessageDao;
import com.jdragon.haoerpdemo.haofangerp.examine.service.TaskMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 金柏宇
 * @version 1.0
 * @date 2020/3/26 22:26
 */
@Service
public class TaskMessageServiceImpl implements TaskMessageService {
    @Autowired
    private TaskMessageDao taskMessageDao;
}
