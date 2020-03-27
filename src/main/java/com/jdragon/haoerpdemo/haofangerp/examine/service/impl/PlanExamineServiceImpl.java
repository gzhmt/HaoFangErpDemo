package com.jdragon.haoerpdemo.haofangerp.examine.service.impl;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.PlanStateEnum;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.examine.component.PaggingBean;
import com.jdragon.haoerpdemo.haofangerp.examine.component.PaggingParams;
import com.jdragon.haoerpdemo.haofangerp.examine.component.exceptions.PageSizeException;
import com.jdragon.haoerpdemo.haofangerp.examine.component.exceptions.TotalException;
import com.jdragon.haoerpdemo.haofangerp.examine.dao.PlanExamineDao;
import com.jdragon.haoerpdemo.haofangerp.examine.service.PlanExamineService;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author 金柏宇
 * @version 1.0
 * @date 2020/3/26 22:26
 */
@Service
public class PlanExamineServiceImpl implements PlanExamineService {
    @Autowired
    private  PlanExamineDao planExamineDao;

    @Autowired
    @Qualifier("examineRedisTemplate")
    private RedisTemplate redisTemplate;

   /* 仅用于缓存计划的状态时作为redis键的前缀
    key格式【prefix:productionNo】*/
    private static final String plansStatePrefix="p_state:";
    /* 存放【生产单号ID】和【创建时间戳】的zset key 利用redis zset的特性，按照时间戳排序 */
    private static final String plansZsetKey="z_plans";
    /* 存放【计划对象】的hash key ，状态另取*/
    private static final String plansHashKey="h_plans";

    @Override
    public List<Plan> getPlanByPagging(PaggingParams params,long total) throws PageSizeException, TotalException {
        PaggingBean paggingBean=new PaggingBean(params,total);//利用封装的bean获取起始索引和结束索引
        List<Plan> plans=new ArrayList<>();//用于存放操作结果
        //若redis缓存有数据，从缓存中获取，否则从数据库获取并存入缓存
            if (redisTemplate.hasKey(plansZsetKey)) {
                Set<String> plansSet= redisTemplate.opsForZSet().range(plansZsetKey,paggingBean.getOffset(),paggingBean.getEnd());
                if(plansSet!=null&&!plansSet.isEmpty()){
                    //执行结果存放在result中
                    List result = (List) redisTemplate.executePipelined((RedisOperations operations)->{
                        //并行流操作，根据生产单号获取对象
                        plansSet.parallelStream().forEach(pro_no->{
                            //从hash中获取对象
                            operations.opsForHash().get(plansHashKey,pro_no);
                            //取计划状态码
                            operations.opsForValue().get(plansStatePrefix+pro_no);
                        });
                        return null;
                    });
                    for(int i=0;i<result.size();i+=2){
                        Plan plan= (Plan) result.get(i);
                        plan.setState(PlanStateEnum.getPlanStateEnumByCode(Integer.parseInt(result.get(i+1).toString())));
                        plans.add(plan);
                    }
                }
            }else {//缓存中没有
                plans = planExamineDao.getPlanByPagging(paggingBean.getOffset(), paggingBean.getCorrectPageSize());
                //放入缓存
                if(!plans.isEmpty()){
                    List<Plan> finalPlans = plans;
                    redisTemplate.executePipelined((RedisOperations operations)->{
                       finalPlans.parallelStream().forEach(plan -> {
                            operations.opsForZSet().add(plansZsetKey,plan.getProductionNo(),plan.getState().getCode());
                            operations.opsForHash().put(plansHashKey,plan.getProductionNo(),plan);
                       });
                       return null;
                   });
                }
            }
        return plans;
    }

    @Override
    @Transactional
    public Result updateState(String productionNo, int examineCode) {
        int result=-1;
        //传入的审批码正确则进行更新操作
        if(examineCode==PlanStateEnum.审核通过.getCode()||examineCode==PlanStateEnum.被驳回.getCode()){
            result=planExamineDao.updateState(productionNo,examineCode);
            if(result>0){
                //从Plan对象中独立出状态字段用于更新
                //更新redis缓存 以 【自定义前缀+生产单号】 为key，以 【计划状态码】为value
                    redisTemplate.opsForValue().set(plansStatePrefix + productionNo, PlanStateEnum.getPlanStateEnumByCode(examineCode).getCode());
                return Result.success("审批成功");
            }else{
                return Result.error("审批失败,没有此生产单号");
            }
        }
        return Result.error("审批码不正确");
    }

    @Override
    public long totalCount() {
        long total=planExamineDao.totalCount();
        return total;
    }
}
