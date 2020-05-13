package com.jdragon.haoerpdemo.haofangerp.production.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jdragon.haoerpdemo.haofangerp.production.constant.EmergencyLevelEnum;
import com.jdragon.haoerpdemo.haofangerp.production.constant.PlanStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
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

    @NotNull(message = "请输入负责人工号")
    @ApiModelProperty(example = "admin")
    private String principalEmployeeNo;

    @NotNull(message = "请输入审批人工号")
    @ApiModelProperty(example = "admin")
    private String approvedEmployeeNo;

    @NotNull(message = "请输入状况")
    private PlanStatusEnum status;

    @NotNull(message = "请输入紧急程度")
    private EmergencyLevelEnum emergencyLevel;

    @ApiModelProperty(example = "2020-03-25 18:59:59")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date closingDate;

    @NotNull(message = "请输入来源")
    @ApiModelProperty(example = "这是来源")
    private String source;

    @NotNull(message = "请输入关联订单")
    @ApiModelProperty(example = "XS-20200323-0001")
    private String linkedOrder;

    @ApiModelProperty(example = "这是备注")
    private String remarks;
}