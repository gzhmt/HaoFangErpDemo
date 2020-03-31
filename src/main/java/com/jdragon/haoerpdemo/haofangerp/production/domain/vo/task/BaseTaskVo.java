package com.jdragon.haoerpdemo.haofangerp.production.domain.vo.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jdragon.haoerpdemo.haofangerp.production.constant.TaskStateEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Auther: kimid
 * @Date: 2020/3/29 20:13
 * @Description:生产任务基础Vo类
 */
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseTaskVo {

    @NotNull(message = "任务名称不能为空")
    @ApiModelProperty(example = "生产任务测试")
    private String taskName;

    @NotNull(message = "操作人不能为空")
    @ApiModelProperty(example = "admin")
    private String operatorEmployeeNo;

    @NotNull(message = "成品名称不能为空")
    @ApiModelProperty(example = "成品1")
    private String productionName;

    @NotNull(message = "成品数量不能为空")
    @ApiModelProperty(example = "10")
    private String productionNumber;

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

    @NotEmpty(message = "成品不能为空")
    private List<TaskProductVo> taskProductVos;

    @NotEmpty(message = "材料不能为空")
    private List<TaskMaterialVo> taskMaterialVos;
}
