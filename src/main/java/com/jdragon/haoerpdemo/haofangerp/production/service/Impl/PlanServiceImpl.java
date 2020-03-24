package com.jdragon.haoerpdemo.haofangerp.production.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.PlanStateEnum;
import com.jdragon.haoerpdemo.haofangerp.commons.tools.Bean2Utils;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.PlanVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import com.jdragon.haoerpdemo.haofangerp.production.mappers.PlanMapper;
import com.jdragon.haoerpdemo.haofangerp.production.service.PlanService;
import com.jdragon.haoerpdemo.haofangerp.security.commons.SecurityContextHolderHelper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.DateUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 17:15
 * @Description: 生产计划服务接口实现类
 */

@Slf4j
@CacheConfig(cacheNames = "plan")
@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper,Plan> implements PlanService {

    @Override
    public IPage<Plan> list(Page<Plan> page){
        LambdaQueryWrapper<Plan> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Plan::getPrincipalEmployeeNo,SecurityContextHolderHelper.getEmployeeNo());
        return baseMapper.selectPage(page,lambdaQueryWrapper);
    }

    @Cacheable(key = "#planVo.productionNo")
    @Override
    public Plan save(PlanVo planVo) throws Exception {
        if(getByProductionNo(planVo.getProductionNo())!=null){
            throw new Exception("已存在该计划单号");
        }
        planVo.setPrincipalEmployeeNo(SecurityContextHolderHelper.getEmployeeNo());
        planVo.setCreateDate(DateUtil.now());
        Plan plan = (Plan)Bean2Utils.copyProperties(planVo,Plan.class);
        if(Optional.ofNullable(plan).isPresent()&&plan.insert()){
            return plan;
        }else{
            throw new Exception("创建失败");
        }
    }

    @CacheEvict
    @Override
    public boolean delete(String productionNo) throws Exception {
        if (isFounder(productionNo)){
            LambdaQueryWrapper<Plan> planLambdaQueryWrapper = new LambdaQueryWrapper<>();
            planLambdaQueryWrapper.eq(Plan::getProductionNo,productionNo);
            return this.remove(planLambdaQueryWrapper);
        }else{
            throw new Exception("不是你创建的生产计划不能删除");
        }
    }

    @CachePut(key = "#planVo.productionNo")
    @Override
    public boolean update(PlanVo planVo) throws Exception {
        if(isFounder(planVo.getProductionNo())) {
            /**
             * 防止传入参数来修改审核状态
             */
            PlanStateEnum state = getById(planVo.getId()).getState();
            Plan plan = (Plan) Bean2Utils.copyProperties(planVo, Plan.class);
            plan.setState(state);
            if(plan.updateById()){
                return true;
            }else{
                throw new Exception("更新失败");
            }
        }else{
            throw new Exception("不是你创建的生产计划不能更改");
        }
    }

    @Cacheable
    @Override
    public Plan getByProductionNo(String productionNo) {
        LambdaQueryWrapper<Plan> planLambdaQueryWrapper = new LambdaQueryWrapper<>();
        planLambdaQueryWrapper.eq(Plan::getProductionNo,productionNo);
        Plan plan = this.getOne(planLambdaQueryWrapper);
        return plan;
    }

    /**
     * 根据计划id判断这个计划
     * @param productionNo
     * @return
     * @throws Exception
     */

    private boolean isFounder(String productionNo) throws Exception {
        Optional<Plan> plan = Optional.ofNullable(this.getByProductionNo(productionNo));
        return SecurityContextHolderHelper.isAuthorities(
                plan.orElse(new Plan()).getPrincipalEmployeeNo());
    }
}
