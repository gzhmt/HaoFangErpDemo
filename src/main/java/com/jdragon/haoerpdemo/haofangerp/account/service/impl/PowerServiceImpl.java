package com.jdragon.haoerpdemo.haofangerp.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Power;
import com.jdragon.haoerpdemo.haofangerp.account.mappers.PowerMapper;
import com.jdragon.haoerpdemo.haofangerp.account.service.PowerService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午4:41
 * @Description: 用户权限服务接口实现类
 */
@CacheConfig(cacheNames = "power")
@Service
public class PowerServiceImpl extends ServiceImpl<PowerMapper, Power> implements PowerService {

    @Cacheable
    @Override
    public List<Power> getPowerByEmployeeNo(String employeeNo) {
        return baseMapper.getPowerByEmployeeNo(employeeNo);
    }
}
