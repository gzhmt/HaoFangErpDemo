package com.jdragon.haoerpdemo.haofangerp.production.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.EmergencyLevelEnum;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.PlanStateEnum;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.PlanStatusEnum;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 20:11
 * @Description: 生产计划vo类
 */
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlanVo {

    private Long id;

    private String productionNo;

    private PlanStateEnum state;

    private PlanStatusEnum status;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private String principalEmployeeNo;

    private EmergencyLevelEnum emergencyLevel;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date closingDate;

    private int source;

    private String linkedOrder;

    private String remarks;
}