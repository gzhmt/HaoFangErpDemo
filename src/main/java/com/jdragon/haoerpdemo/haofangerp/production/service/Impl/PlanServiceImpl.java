package com.jdragon.haoerpdemo.haofangerp.production.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdragon.haoerpdemo.haofangerp.production.constant.PlanAuditStatusEnum;
import com.jdragon.haoerpdemo.haofangerp.production.constant.PlanStateEnum;
import com.jdragon.haoerpdemo.haofangerp.commons.tools.AutoGenerateUtil;
import com.jdragon.haoerpdemo.haofangerp.commons.tools.Bean2Utils;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.PlanVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import com.jdragon.haoerpdemo.haofangerp.production.mappers.PlanMapper;
import com.jdragon.haoerpdemo.haofangerp.production.service.PlanService;
import com.jdragon.haoerpdemo.haofangerp.security.commons.SecurityContextHolderHelper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.Test;
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

    @Test
    public void test(){
        System.out.println(PlanStateEnum.valueOf("新计划").getCode());
    }

    @Override
    public IPage<Plan> list(Page<Plan> page,String state){
        LambdaQueryWrapper<Plan> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Plan::getPrincipalEmployeeNo,
                SecurityContextHolderHelper.getEmployeeNo())
                .eq(Plan::isDeleted,false);
        if(Optional.ofNullable(state).isPresent()){
            lambdaQueryWrapper.eq(Plan::getState,PlanStateEnum.valueOf(state).getCode());
        }
        return baseMapper.selectPage(page,lambdaQueryWrapper);
    }

    /**
     * @Author: Jdragon
     * @Date: 2020.03.25 下午 2:08
     * @params: [planVo]
     * @return: com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan
     * @Description: 保存plan，传入的vo，会强制修改productionNo,createDate,state
     **/
    @CachePut(key = "#result.productionNo")
    @Override
    public Plan save(PlanVo planVo) {
        synchronized (this) {
            Plan plan = (Plan) Bean2Utils.copyProperties(planVo, Plan.class);
            if (planInit(plan).insert()) {
                return plan;
            } else {
                throw new UnknownError("创建失败");
            }
        }
    }

    @CachePut(key = "#result.productionNo")
    @Override
    public Plan copy(String productionNo) throws Exception {
        Plan plan = baseMapper.selectByProductionNo(productionNo);
        if(!Optional.ofNullable(plan).isPresent()){
            throw new Exception("无该计划，无法复制");
        }
        if(planInit(plan).insert()){
            return plan;
        }else{
            throw new UnknownError("复制失败");
        }
    }
    @CacheEvict
    @Override
    public boolean delete(String productionNo) throws Exception {
        if (isFounder(productionNo)){
            Plan plan = baseMapper.selectByProductionNo(productionNo);
            if(Optional.ofNullable(plan).isPresent()){
                if(plan.deleteById()){
                    return true;
                }else{
                    throw new UnknownError("删除失败");
                }
            }else{
                throw new Exception("无该计划，无法删除");
            }
        }else{
            throw new Exception("不是你管理的生产计划不能删除");
        }
    }

    @Cacheable
    @Override
    public Plan getByProductionNo(String productionNo) throws Exception {
        LambdaQueryWrapper<Plan> planLambdaQueryWrapper = new LambdaQueryWrapper<>();
        planLambdaQueryWrapper.eq(Plan::getProductionNo,productionNo).eq(Plan::isDeleted,false);
        Plan plan = this.getOne(planLambdaQueryWrapper);
        if(Optional.ofNullable(plan).isPresent()){
            return plan;
        }else{
            throw new Exception("没有这个生产单号的计划");
        }
    }

    /**
     * @Author: Jdragon
     * @Date: 2020.03.29 下午 8:20
     * @params: [plan]
     * @return: com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan
     * @Description: 创建和复制都需要做的初始化plan
     **/
    private Plan planInit(Plan plan){
        Plan lastPlan = baseMapper.selectByIdDescLimitOne();
        if (Optional.ofNullable(lastPlan).isPresent()) {
            plan.setProductionNo(AutoGenerateUtil.createIncreaseOdd(lastPlan.getProductionNo()));
        } else {
            plan.setProductionNo(AutoGenerateUtil.createTodayFirstOdd("SC"));
        }
        plan.setCreateEmployeeNo(SecurityContextHolderHelper.getEmployeeNo());
        plan.setCreateDate(DateUtil.now());
        plan.setState(PlanStateEnum.新计划);
        plan.setAuditStatus(PlanAuditStatusEnum.待审核);
        return plan;
    }

    /**
     * @Author: Jdragon
     * @Date: 2020.03.25 下午 3:44
     * @params: [productionNo]
     * @return: boolean
     * @Description:根据计划单号判断这个计划是否是你创建，或是你负责的
     **/
    private boolean isFounder(String productionNo) throws Exception {
        Optional<Plan> plan = Optional.ofNullable(this.getByProductionNo(productionNo));
        boolean isPrincipal = SecurityContextHolderHelper.isAuthorities(
                plan.orElse(new Plan()).getPrincipalEmployeeNo());
        boolean isCreate = SecurityContextHolderHelper.isAuthorities(
                plan.orElse(new Plan()).getCreateEmployeeNo());
        return isPrincipal||isCreate;
    }
}
