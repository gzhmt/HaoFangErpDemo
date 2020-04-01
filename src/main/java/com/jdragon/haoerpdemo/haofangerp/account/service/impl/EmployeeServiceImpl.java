package com.jdragon.haoerpdemo.haofangerp.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Employee;
import com.jdragon.haoerpdemo.haofangerp.account.domain.vo.EmployeeVo;
import com.jdragon.haoerpdemo.haofangerp.account.mappers.EmployeeMapper;
import com.jdragon.haoerpdemo.haofangerp.account.service.EmployeeService;
import com.jdragon.haoerpdemo.haofangerp.commons.tools.Bean2Utils;
import com.jdragon.haoerpdemo.haofangerp.security.commons.BCryptPasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午4:30
 * @Description: 用户服务接口实现类
 */
@Service
@CacheConfig(cacheNames = "employee")
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil;

    @Cacheable
    @Override
    public Employee getEmployeeByEmployeeNo(String employeeNo) {
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Employee::getEmployeeNo,employeeNo);
        Employee employee = getOne(lambdaQueryWrapper);
        return employee;
    }

    @Override
    public boolean checkLogin(String employeeNo, String rawPassword) throws Exception {
        Employee employee = this.getEmployeeByEmployeeNo(employeeNo);
        if(employee == null){
            throw new Exception("账号不存在，请重新尝试！");
        }else{
            //加密的密码
            String encodedPassword = employee.getPassword();
            //和加密后的密码进行比配
            if(!bCryptPasswordEncoderUtil.matches(rawPassword,encodedPassword)) {
                throw new Exception("密码不正确！");
            }else{
                return true;
            }
        }
    }

    @Cacheable(key = "#employeeVo.employeeNo")
    @Override
    public Employee register(EmployeeVo employeeVo) throws Exception {
        if(employeeVo !=null) {
            Employee employee = this.getEmployeeByEmployeeNo(employeeVo.getEmployeeNo());
            if(employee != null) {
                throw new Exception("这个用户已经存在，不能重复。");
            }
            employee = (Employee) Bean2Utils.copyProperties(employeeVo, Employee.class);
            employee.setPassword(bCryptPasswordEncoderUtil.encode(employeeVo.getPassword()));
            if(employee.insert()){
                return employee;
            }else{
                throw new Exception("注册失败");
            }
        }else{
            throw new Exception("错误消息：用户对象为空！");
        }
    }
}
