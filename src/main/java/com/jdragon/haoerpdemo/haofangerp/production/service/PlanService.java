package com.jdragon.haoerpdemo.haofangerp.production.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.PlanVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 17:14
 * @Description: 生产计划服务接口类
 */


public interface PlanService extends IService<Plan> {
    IPage<Plan> list(Page<Plan> page);
    Plan save(PlanVo planVo);
    boolean delete(Long id);
    boolean update(PlanVo planVo);
    Plan getById(int id);
}
