package com.jdragon.haoerpdemo.haofangerp.production.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jdragon.haoerpdemo.haofangerp.production.constant.TaskStateEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 20:49
 * @Description: 生产任务vo类
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskVo {

    @ApiModelProperty(hidden = true)
    private Long id;

    @ApiModelProperty(hidden = true)
    private String taskNo;

    @NotNull(message = "任务名称不能为空")
    @ApiModelProperty(example = "生产任务测试")
    private String taskName;

    @ApiModelProperty(example = "LS-20200323-0001",hidden = true)
    private String sequenceId;

    @NotNull(message = "计划单号名称不能为空")
    @ApiModelProperty(example = "SC-20200326-0001")
    private String productionPlanNo;

    @ApiModelProperty(example = "admin")
    private String operatorEmployeeNo;

    @NotNull(message = "成品名称不能为空")
    @ApiModelProperty(example = "成品1")
    private String productionName;

    @NotNull(message = "成品数量不能为空")
    @ApiModelProperty(example = "10")
    private String productionNumber;

    @ApiModelProperty(hidden = true)
    private TaskStateEnum state;

    @ApiModelProperty(hidden = true)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stateChangeDate;

    @ApiModelProperty(hidden = true)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @NotNull(message = "预计开始时间不能为空")
    @ApiModelProperty(example = "2020-03-23 18:59:59")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date forecastStartDate;

    @NotNull(message = "预计结束时间不能为空")
    @ApiModelProperty(example = "2020-03-23 18:59:59")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date forecastEndDate;

    @ApiModelProperty(example = "备注")
    private String remarks;

    @NotEmpty
    private List<TaskMaterialVo> taskMaterialVos;
    @NotEmpty
    private  List<TaskProductVo> taskProductVos;


}
