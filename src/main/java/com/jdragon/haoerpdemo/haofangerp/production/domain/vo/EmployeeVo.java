package com.jdragon.haoerpdemo.haofangerp.production.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.20 11:55
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
