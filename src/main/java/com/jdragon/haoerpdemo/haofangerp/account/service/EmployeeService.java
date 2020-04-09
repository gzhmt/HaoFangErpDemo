package com.jdragon.haoerpdemo.haofangerp.account.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Employee;
import com.jdragon.haoerpdemo.haofangerp.account.domain.vo.EmployeeVo;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午4:30
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
    boolean checkLogin(String name, String rawPassword) throws Exception;

    /**
     * 注册
     * @param employeeVo
     * @return member
     * @throws Exception
     */
    Employee register(EmployeeVo employeeVo) throws Exception;


    IPage<Employee> listEmployees(Page<Employee> page);
}

