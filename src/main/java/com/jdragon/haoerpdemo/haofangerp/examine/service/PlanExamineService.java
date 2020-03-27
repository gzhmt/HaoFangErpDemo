package com.jdragon.haoerpdemo.haofangerp.examine.service;

import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.examine.component.PaggingParams;
import com.jdragon.haoerpdemo.haofangerp.examine.component.exceptions.PageSizeException;
import com.jdragon.haoerpdemo.haofangerp.examine.component.exceptions.TotalException;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author 金柏宇
 * @version 1.0
 * @date 2020/3/26 22:22
 */
@Service
public interface PlanExamineService {
    List<Plan> getPlanByPagging(PaggingParams params,long total) throws PageSizeException, TotalException;
    Result updateState(String productionNo, int examineCode);
    long totalCount();
}
