package com.jdragon.haoerpdemo.haofangerp.account.power.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jdragon.haoerpdemo.haofangerp.account.power.domain.entity.Power;

import java.util.List;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午4:17
 * @Description: 用户权限服务接口类
 */
public interface PowerService extends IService<Power> {

    List<Power> getPowerByEmployeeNo(String employeeNo);

}
