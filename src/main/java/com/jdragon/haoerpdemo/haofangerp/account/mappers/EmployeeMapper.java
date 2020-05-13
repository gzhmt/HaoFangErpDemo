package com.jdragon.haoerpdemo.haofangerp.account.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Employee;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.EmployeeRole;
import com.jdragon.haoerpdemo.haofangerp.account.domain.provider.EmployeeProvider;
import com.jdragon.haoerpdemo.haofangerp.account.domain.provider.PowerProvider;
import com.jdragon.haoerpdemo.haofangerp.account.domain.vo.LatestEmployeeVo;
import com.jdragon.haoerpdemo.haofangerp.account.domain.vo.QueryEmployeeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午4:29
 * @Description: 用户仓储层
 */
@Mapper
@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 模糊查询员工列表
     * @param keyWord 关键词(工号,名字)
     * @return
     */
    @SelectProvider(type = EmployeeProvider.class,method = "listEmployees")
    List<QueryEmployeeVo> listEmployees(String keyWord);

    /**
     * 查询当前登录员工个人信息
     * @param employeeNo 工号
     * @return
     */
    @Select("select employee_no,e.name as employeeName,birth,entry_time,d.name as departmentName,tel,id_card,photo_url " +
            "from system_employee e " +
            "join system_department d on e.department_id = d.id " +
            "where e.employee_no = #{employeeNo}")
    LatestEmployeeVo getLoginEmployeeInfo(String employeeNo);

}

