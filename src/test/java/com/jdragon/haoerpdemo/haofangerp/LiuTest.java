package com.jdragon.haoerpdemo.haofangerp;

import com.jdragon.haoerpdemo.haofangerp.commons.constant.PlanStateEnum;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Task;
import com.jdragon.haoerpdemo.haofangerp.production.service.Impl.PlanServiceImpl;
import com.jdragon.haoerpdemo.haofangerp.production.service.PlanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * @Auther: kimid
 * @Date: 2020/3/24 18:04
 * @Description:
 */
public class LiuTest {


    PlanService planService = new PlanServiceImpl();
    @Test
    public void test1(){
        Plan plan = planService.getByProductionNo("44");
        System.out.println(plan);

    }
}