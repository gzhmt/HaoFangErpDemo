package com.jdragon.haoerpdemo.haofangerp.production.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.PlanStateEnum;
import com.jdragon.haoerpdemo.haofangerp.commons.tools.Bean2Utils;
import com.jdragon.haoerpdemo.haofangerp.commons.tools.Date2Util;
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

import java.text.MessageFormat;
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

    private String dateFormat = "yyyyMMdd";

    private String productionFormat = "SC-{0}-{1}";

    @Override
    public IPage<Plan> list(Page<Plan> page){
        LambdaQueryWrapper<Plan> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Plan::getPrincipalEmployeeNo,SecurityContextHolderHelper.getEmployeeNo());
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
    public Plan save(PlanVo planVo) throws Exception {
        LambdaQueryWrapper<Plan> planLambdaQueryWrapper = new LambdaQueryWrapper<>();
        planLambdaQueryWrapper.orderByDesc(Plan::getId).last("limit 1");
        Plan lastPlan = baseMapper.selectOne(planLambdaQueryWrapper);
        /*
          |-检测历史计划最后创建的计划，没有则直接使用 SC-{今日日期}-{0001}，如果有则进入条件
               |-将单号按 - 号分隔，分隔之后传日期部分进行对比，返回结果
                   |-如果今日创建过计划单号，则根据上次生成的第三部分+1来生成
                   |-如果今日没有创建过计划单号，则使用0001
         */
        String newPlanProductionThreePartStr;/*SC-20200325-0001这个变量为最后部分的0001的生成*/
        if(Optional.ofNullable(lastPlan).isPresent()){
            String[] productionNoSplit = lastPlan.getProductionNo().split("-");
            boolean lastPlanCreateIsToday = Date2Util.contrastNowDateStr(productionNoSplit[1],dateFormat);
            if(lastPlanCreateIsToday){
                int newPlanProductionThreePart = Integer.parseInt(productionNoSplit[2])+1;
                newPlanProductionThreePartStr = String.format("%04d",newPlanProductionThreePart);
            }else{
                newPlanProductionThreePartStr = String.format("%04d",1);
            }
        }else{
            newPlanProductionThreePartStr = String.format("%04d",1);
        }
        //使用SC-{}-{}格式占位符来生成生产单号
        planVo.setProductionNo(MessageFormat.format(productionFormat,
                Date2Util.now(dateFormat), newPlanProductionThreePartStr));

        planVo.setCreateEmployeeNo(SecurityContextHolderHelper.getEmployeeNo());
        planVo.setCreateDate(DateUtil.now());
        planVo.setState(PlanStateEnum.新计划);
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
            if(this.remove(planLambdaQueryWrapper)){
                return true;
            }else{
                throw new Exception("删除失败");
            }
        }else{
            throw new Exception("不是你管理的生产计划不能删除");
        }
    }

    @CachePut(key = "#planVo.productionNo")
    @Override
    public boolean update(PlanVo planVo) throws Exception {
        if(isFounder(planVo.getProductionNo())) {
            /*
              防止传入参数来修改审核状态
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
            throw new Exception("不是你管理的生产计划不能更改");
        }
    }

    @Cacheable
    @Override
    public Plan getByProductionNo(String productionNo) throws Exception {
        LambdaQueryWrapper<Plan> planLambdaQueryWrapper = new LambdaQueryWrapper<>();
        planLambdaQueryWrapper.eq(Plan::getProductionNo,productionNo);
        Plan plan = this.getOne(planLambdaQueryWrapper);
        if(Optional.ofNullable(plan).isPresent()){
            return plan;
        }else{
            throw new Exception("没有这个生产单号的计划");
        }

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
