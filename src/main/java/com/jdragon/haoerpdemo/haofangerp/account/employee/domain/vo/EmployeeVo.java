package com.jdragon.haoerpdemo.haofangerp.account.employee.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午4:33
 * @Description: 用户vo类
 */
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeVo {
    private int id;

    private String employeeNo;

    private String password;

    private String name;

    private Date birth;

    private Date entryTime;
}
