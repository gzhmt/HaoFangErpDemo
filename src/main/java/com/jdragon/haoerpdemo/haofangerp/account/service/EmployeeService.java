package com.jdragon.haoerpdemo.haofangerp.account.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Employee;
import com.jdragon.haoerpdemo.haofangerp.account.domain.vo.EmployeeVo;
import com.jdragon.haoerpdemo.haofangerp.account.domain.vo.LatestEmployeeVo;
import com.jdragon.haoerpdemo.haofangerp.account.domain.vo.ModifyEmployeeVo;
import com.jdragon.haoerpdemo.haofangerp.account.domain.vo.QueryEmployeeVo;
import com.jdragon.haoerpdemo.haofangerp.commons.exceptions.HFException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

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
    Employee register(EmployeeVo employeeVo) throws HFException;

    /**
     * 分页查询员工列表
     * @param pageNo
     * @param pageSize
     * @param keyWord
     * @return
     */
    PageInfo<QueryEmployeeVo> listEmployees(int pageNo, int pageSize, String keyWord);

    /**
     * 上传员工头像
     * @param avatarFile 头像文件
     * @param request
     * @return
     * @throws Exception
     */
    String uploadAvatar(MultipartFile avatarFile, HttpServletRequest request) throws HFException;

    /**
     * 修改员工个人信息
     * @param modifyEmployeeVo
     * @return
     */
    boolean updateEmployeeInfo(ModifyEmployeeVo modifyEmployeeVo) throws HFException;

    /**
     * 回显当前登录员工个人信息
     * @return
     */
    LatestEmployeeVo getLoginEmployeeInfo();

    /**
     * 重置员工密码
     * @param password
     * @return
     * @throws Exception
     */
    boolean resetEmployeePassword(String password) throws HFException;
}

