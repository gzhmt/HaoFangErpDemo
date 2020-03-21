package com.jdragon.haoerpdemo.haofangerp.production.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Power;
import com.jdragon.haoerpdemo.haofangerp.production.mappers.PowerMapper;
import com.jdragon.haoerpdemo.haofangerp.production.service.PowerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.20 14:01
 * @Description: 用户权限服务接口实现类
 */
@Service
public class PowerServiceImpl extends ServiceImpl<PowerMapper, Power> implements PowerService {
    @Override
    public List<Power> getPowerByEmployeeNo(String employeeNo) {
//        List<Power> powers = new ArrayList<>();
//        powers.add(new Power(1,"所有API","/**","GET,POST,PUT,DELETE",100,0,null));
//        return powers;
        return baseMapper.getPowerByEmployeeNo(employeeNo);
    }
}
