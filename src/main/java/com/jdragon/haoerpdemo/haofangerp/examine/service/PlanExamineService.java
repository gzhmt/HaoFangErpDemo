package com.jdragon.haoerpdemo.haofangerp.examine.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.examine.component.exceptions.PaggingParamsException;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import org.springframework.stereotype.Service;



/**
 * @author 金柏宇
 * @version 1.0
 * @date 2020/3/26 22:22
 */
@Service
public interface PlanExamineService{
    IPage<Plan> getPlanByPagging(Page<Plan> page) throws PaggingParamsException;
    Result updateState(String productionNo, int examineCode);
}
