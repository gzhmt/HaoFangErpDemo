package com.jdragon.haoerpdemo.haofangerp.production.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jdragon.haoerpdemo.haofangerp.commons.exceptions.HFException;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.PlanVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 17:14
 * @Description: 生产计划服务接口类
 */


public interface PlanService extends IService<Plan> {
    IPage<Plan> list(Page<Plan> page, String state);

    Plan save(PlanVo planVo) throws HFException;

    boolean delete(String productionNo) throws HFException;

    Plan getByProductionNo(String productionNo) throws HFException;

    Plan copy(String productionNo) throws HFException;

    Plan update(String productionNo, PlanVo planVo) throws HFException;

    List<Map<String, String>> copyPlans(String[] productionNoList);

    List<Map<String, String>> deletePlans(String[] productionNoList);

    /**
     * 模糊查询计划号
     */
    List<String> getFuzzyPlanName(String fuzzy);
}
