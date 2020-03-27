package com.jdragon.haoerpdemo.haofangerp.examine.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 用于计划审核模块的Bean自定义配置
 *
 * @author 金柏宇
 * @version 1.0
 * @date 2020/3/26 22:43
 */
@Configuration
public class ExamineConfig {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 用于examine计划审核模块的redis模板类
     *
     * @return 获取用于操作redis的自定义模板类
     */
    @Bean("examineRedisTemplate")
    public RedisTemplate getExamineRedisTemplate(){
        RedisTemplate redisTemplate=new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(redisTemplate.getStringSerializer());
        redisTemplate.setHashKeySerializer(redisTemplate.getStringSerializer());
        return redisTemplate;
    }
}
