package com.jdragon.haoerpdemo.haofangerp.production.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.EmergencyLevelEnum;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.PlanStateEnum;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.PlanStatusEnum;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(hidden = true)
    private Long id;

    @ApiModelProperty(example = "创建人工号",hidden = true)
    private String createEmployeeNo;

    @ApiModelProperty(example = "admin")
    private String principalEmployeeNo;

    @ApiModelProperty(example = "SC-20200323-0001",hidden = true)
    private String productionNo;

    @ApiModelProperty(example = "新计划",hidden = true)
    private PlanStateEnum state;

    private PlanStatusEnum status;

    @ApiModelProperty(example = "2020-03-23 18:59:59",hidden = true)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private EmergencyLevelEnum emergencyLevel;

    @ApiModelProperty(example = "2020-03-25 18:59:59")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date closingDate;

    @ApiModelProperty(example = "1")
    private int source;

    @ApiModelProperty(example = "XS-20200323-0001")
    private String linkedOrder;

    @ApiModelProperty(example = "生产单号为SC-20200323-0001的生产计划")
    private String remarks;
}