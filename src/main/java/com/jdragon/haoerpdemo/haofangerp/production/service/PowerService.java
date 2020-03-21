package com.jdragon.haoerpdemo.haofangerp.production.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Power;

import java.util.List;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.20 14:01
 * @Description: 用户权限服务接口类
 */
public interface PowerService extends IService<Power> {

    List<Power> getPowerByEmployeeNo(String employeeNo);

}
