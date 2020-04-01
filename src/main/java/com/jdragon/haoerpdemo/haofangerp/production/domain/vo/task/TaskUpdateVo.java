package com.jdragon.haoerpdemo.haofangerp.production.domain.vo.task;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jdragon.haoerpdemo.haofangerp.production.constant.TaskStateEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @Auther: kimid
 * @Date: 2020/3/29 23:02
 * @Description:
 */
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskUpdateVo extends BaseTaskVo{
    /**
     * 根据任务编号修改任务
     */
    @NotNull(message = "任务编号不能为空")
    @ApiModelProperty(example = "SC-20200327-0005")
    private String taskNo;
    /**
     * 可以修改状态
     */
    @NotNull(message = "任务状态不能为空")
    @ApiModelProperty(example = "正常")
    private TaskStateEnum state;

}