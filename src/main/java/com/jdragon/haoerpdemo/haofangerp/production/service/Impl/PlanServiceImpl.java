package com.jdragon.haoerpdemo.haofangerp.production.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdragon.haoerpdemo.haofangerp.commons.tools.Bean2Utils;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.PlanVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import com.jdragon.haoerpdemo.haofangerp.production.mappers.PlanMapper;
import com.jdragon.haoerpdemo.haofangerp.production.service.PlanService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 17:15
 * @Description: 生产计划服务接口实现类
 */
@CacheConfig(cacheNames = "plan")
@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper,Plan> implements PlanService {

    @Override
    public IPage<Plan> list(Page<Plan> page) {
        return baseMapper.selectPage(page,null);
    }

    @Override
    public Plan save(PlanVo planVo) {
        Plan plan = (Plan)Bean2Utils.copyProperties(planVo,Plan.class);
        return plan.insert()?plan:null;
    }

    @Override
    public boolean delete(Long id) {
        return baseMapper.deleteById(id)>0;
    }

    @Override
    public boolean update(PlanVo planVo) {
        Plan plan = (Plan)Bean2Utils.copyProperties(planVo,Plan.class);
        return plan.updateById();
    }

    @Cacheable
    @Override
    public Plan getById(int id) {
        return baseMapper.selectById(id);
    }
}
