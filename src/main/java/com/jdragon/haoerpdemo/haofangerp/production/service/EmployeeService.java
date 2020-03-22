package com.jdragon.haoerpdemo.haofangerp.production.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.EmployeeVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Employee;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.20 11:26
 * @Description: 用户服务接口类
 */
public interface EmployeeService extends IService<Employee> {


    /**
     * 通过账号查询用户
     * @param employeeNo
     * @return
     */
    Employee getEmployeeByEmployeeNo(String employeeNo);

    /**
     * 个性化验证登录
     * @param name 账号
     * @param rawPassword 原始密码
     * @return
     */
    boolean checkLogin(String name,String rawPassword) throws Exception;

    /**
     * 注册
     * @param employeeVo
     * @return member
     * @throws Exception
     */
    Employee register(EmployeeVo employeeVo) throws Exception;
}
