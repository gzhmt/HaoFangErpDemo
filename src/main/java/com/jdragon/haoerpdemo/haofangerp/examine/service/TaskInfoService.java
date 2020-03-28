package com.jdragon.haoerpdemo.haofangerp.examine.service;

import com.jdragon.haoerpdemo.haofangerp.examine.component.PaggingParams;
import com.jdragon.haoerpdemo.haofangerp.examine.component.exceptions.PageSizeException;
import com.jdragon.haoerpdemo.haofangerp.examine.component.exceptions.TotalException;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.TaskVo;

import java.util.List;

/**
 * @author 金柏宇
 * @version 1.0
 * @date 2020/3/26 22:21
 */
public interface TaskInfoService {
    List<TaskVo> getTaskByPagging(String productionNo, PaggingParams params,long total) throws PageSizeException, TotalException;
    long getTaskToal();
}
