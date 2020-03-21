package com.jdragon.haoerpdemo.haofangerp.production.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.TaskStateEnum;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 20:49
 * @Description: 生产任务vo类
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskVo {
    private int id;

    private String taskNo;

    private String taskName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date productionDate;

    private int operatorMember;

    private TaskStateEnum state;


    private String remarks;

    private String productName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date forecastStartTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date forecastEndTime;

    private int productNumber;

    private int productionPlanId;

    private int sequenceId;
}
