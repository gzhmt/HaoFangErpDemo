package com.jdragon.haoerpdemo.haofangerp.production.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.TaskStateEnum;
import io.swagger.annotations.ApiModelProperty;
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

    private Long id;

    private String taskNo;

    @ApiModelProperty(example = "生产任务测试")
    private String taskName;


    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    //@ApiModelProperty(example = "admin")
    private String operatorEmployeeNo;

    private TaskStateEnum state;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stateChangeDate;

    @ApiModelProperty(example = "生产单号为SC-20200323-0001的生产计划下的生产任务")
    private String remarks;

    @ApiModelProperty(example = "2020-03-23 18:59:59")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date forecastStartTime;

    @ApiModelProperty(example = "2020-03-23 18:59:59")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date forecastEndTime;


    @ApiModelProperty(example = "SC-20200323-0001")
    private String productionPlanNo;

    private Long productionPlanId;


    //@ApiModelProperty(example = "LS-20200323-0001")
    private String sequenceId;
}
