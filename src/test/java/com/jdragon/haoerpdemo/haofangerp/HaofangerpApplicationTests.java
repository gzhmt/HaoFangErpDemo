package com.jdragon.haoerpdemo.haofangerp;

import com.baomidou.mybatisplus.extension.api.R;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.commons.tools.AutoGenerateUtil;
import com.jdragon.haoerpdemo.haofangerp.commons.tools.Date2Util;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Plan;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@SpringBootTest
class HaofangerpApplicationTests {


    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void test01(){
//        String msg = stringRedisTemplate.opsForValue().get("msg");
//        System.out.println(msg);
    }

    @Test
    void contextLoads()  {
        String lastPlan = "SC-20200325-0001";
        String[] productionNoSplit = "SC-20200325-0001".split("-");
        boolean lastPlanCreateToday = Date2Util.contrastNowDateStr(productionNoSplit[1],"yyMMdd");
        System.out.println(lastPlanCreateToday);

        System.out.println(AutoGenerateUtil.createIncreaseOdd(lastPlan));

        System.out.println(AutoGenerateUtil.createTodayFirstOdd("SC"));
    }
}
