package com.jdragon.haoerpdemo.haofangerp.production.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Employee;
import com.jdragon.haoerpdemo.haofangerp.account.service.EmployeeService;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.ResultCode;
import com.jdragon.haoerpdemo.haofangerp.commons.exceptions.HFException;
import com.jdragon.haoerpdemo.haofangerp.production.constant.BatchResultType;
import com.jdragon.haoerpdemo.haofangerp.production.constant.BatchResult;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 17:15
 * @Description: 生产计划服务接口实现类
 */

@Slf4j
@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper,Plan> implements PlanService {

    @Autowired
    EmployeeService employeeService;

    @Test
    public void test(){
        System.out.println(BatchResultType.消息);
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
    @Override
    public synchronized Plan save(PlanVo planVo) throws HFException {
        String principalEmployeeNo = planVo.getPrincipalEmployeeNo();
        String approvedEmployeeNo = planVo.getApprovedEmployeeNo();

        Employee principalEmployee = employeeService.getEmployeeByEmployeeNo(principalEmployeeNo);
        Employee approvedEmployee = employeeService.getEmployeeByEmployeeNo(approvedEmployeeNo);

        if(principalEmployee==null){
            throw new HFException("负责人不存在");
        }else if(approvedEmployee==null){
            throw new HFException("审批人不存在");
        }

        Plan plan = (Plan) Bean2Utils.copyProperties(planVo, Plan.class);
        if (planInit(plan).insert()) {
            return plan;
        } else {
            throw new UnknownError("创建失败");
        }
    }

    @Override
    public synchronized Plan copy(String productionNo) throws HFException {
        Plan plan = baseMapper.selectByProductionNo(productionNo);
        if (!Optional.ofNullable(plan).isPresent()) {
            throw new HFException("无该计划，无法复制");
        }
        if (planInit(plan).insert()) {
            return plan;
        } else {
            throw new UnknownError("复制失败");
        }
    }

    @Override
    public boolean delete(String productionNo) throws HFException {
        if (!isFounder(productionNo)) {
            throw new HFException("不是你管理的生产计划不能删除");
        }
        Plan plan = baseMapper.selectByProductionNo(productionNo);
        if(Optional.ofNullable(plan).isPresent()){
            if(plan.deleteById()){
                return true;
            }else{
                throw new UnknownError("删除失败");
            }
        }else{
            throw new HFException("无该计划，无法删除");
        }
    }
    @Override
    public Plan update(String productionNo,PlanVo planVo) throws HFException {
        if(!isFounder(productionNo)){
            throw new HFException("不是你管理的生产计划不能更改");
        }
        Plan plan = baseMapper.selectByProductionNo(productionNo);
        if(plan==null){
            throw new HFException("没有这个计划");
        }
        if(plan.getStatus().equals(PlanStateEnum.生产中)){
            throw new HFException("正在生产，无法更改，请等待生产完毕");
        }
        BeanUtils.copyProperties(planVo,plan);
        if(plan.updateById()){
            return plan;
        }else{
            throw new UnknownError("更新失败");
        }
    }


    @Override
    public List<Map<String,String>> copyPlans(String[] productionNoList) {
        List<Map<String,String>> copyResults = new LinkedList<>();
        for(String productionNo:productionNoList){
            Map<String,String> copyResult = new HashMap<>();
            copyResults.add(copyResult);
            copyResult.put(BatchResultType.编号.getDes(),productionNo);
            try {
                Plan plan = copy(productionNo);
                if(Optional.ofNullable(plan).isPresent()){
                    copyResult.put(BatchResultType.消息.getDes(),plan.getProductionNo());
                    copyResult.put(BatchResultType.结果.getDes(), BatchResult.成功.getDes());
                }else{
                    copyResult.put(BatchResultType.消息.getDes(),"未知原因复制失败");
                    copyResult.put(BatchResultType.结果.getDes(),BatchResult.失败.getDes());
                }
            } catch (Exception e) {
                copyResult.put(BatchResultType.消息.getDes(),e.getMessage());
                copyResult.put(BatchResultType.结果.getDes(),BatchResult.失败.getDes());
            }catch (UnknownError e){
                copyResult.put(BatchResultType.消息.getDes(),
                        ResultCode.SYSTEM_UN_KNOW_ERROR.getMessage()+e.getMessage());
                copyResult.put(BatchResultType.结果.getDes(),BatchResult.失败.getDes());
            }
        }
        return copyResults;
    }

    @Override
    public  List<Map<String,String>> deletePlans(String[] productionNoList) {
        List<Map<String,String>> deleteResults = new LinkedList<>();
        for(String productionNo:productionNoList){
            Map<String,String> deleteResult = new HashMap<>();
            deleteResults.add(deleteResult);
            deleteResult.put(BatchResultType.编号.getDes(),productionNo);
            try{
                deleteResult.put(BatchResultType.消息.getDes(), String.valueOf(delete(productionNo)));
                deleteResult.put(BatchResultType.结果.getDes(),BatchResult.成功.getDes());
            }catch (Exception e){
                deleteResult.put(BatchResultType.消息.getDes(),e.getMessage());
                deleteResult.put(BatchResultType.结果.getDes(),BatchResult.失败.getDes());
            }catch (UnknownError e){
                deleteResult.put(BatchResultType.消息.getDes(),
                        ResultCode.SYSTEM_UN_KNOW_ERROR.getMessage()+e.getMessage());
                deleteResult.put(BatchResultType.结果.getDes(),BatchResult.失败.getDes());
            }
        }
        return deleteResults;
    }

    @Override
    public List<String> getFuzzyPlanName(String fuzzyName) {
        fuzzyName =  fuzzyName==null?"%":fuzzyName;
        return baseMapper.selectFuzzyPlanName("%"+fuzzyName+"%");
    }


    @Override
    public Plan getByProductionNo(String productionNo) throws HFException {
        LambdaQueryWrapper<Plan> planLambdaQueryWrapper = new LambdaQueryWrapper<>();
        planLambdaQueryWrapper.eq(Plan::getProductionNo,productionNo).eq(Plan::isDeleted,false);
        Plan plan = this.getOne(planLambdaQueryWrapper);
        if(Optional.ofNullable(plan).isPresent()){
            return plan;
        }else{
            throw new HFException("没有这个生产单号的计划");
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
        plan.setState(PlanStateEnum.新任务);
        plan.setAuditStatus(PlanAuditStatusEnum.待审核);
        return plan;
    }

    /**
     * @Author: Jdragon
     * @Date: 2020.03.25 下午 3:44
     * @params: [productionNo]
     * @return: boolean
     * @Description: 根据计划单号判断这个计划是否是你创建，或是你负责的
     **/
    private boolean isFounder(String productionNo) throws HFException {
        Optional<Plan> plan = Optional.ofNullable(this.getByProductionNo(productionNo));
        boolean isPrincipal = SecurityContextHolderHelper.isAuthorities(
                plan.orElse(new Plan()).getPrincipalEmployeeNo());
        boolean isCreate = SecurityContextHolderHelper.isAuthorities(
                plan.orElse(new Plan()).getCreateEmployeeNo());
        return isPrincipal||isCreate;
    }
}
