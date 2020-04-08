package com.jdragon.haoerpdemo.haofangerp.examine.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdragon.haoerpdemo.haofangerp.examine.component.exceptions.PaggingParamsException;
import com.jdragon.haoerpdemo.haofangerp.examine.domain.vo.ExamineTaskDetailVo;
/**
 * @author 金柏宇
 * @version 1.0
 * @date 2020/3/26 22:21
 */
public interface TaskInfoService {
    IPage<ExamineTaskDetailVo> getTaskByPagging(String productionNo, Page<ExamineTaskDetailVo> page) throws PaggingParamsException;
}
