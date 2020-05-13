package com.jdragon.haoerpdemo.haofangerp.account.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/5/13 上午11:02
 * @Description
 */
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QueryEmployeeVo {

    private int id;

    private String employeeNo;

    private String name;

}
