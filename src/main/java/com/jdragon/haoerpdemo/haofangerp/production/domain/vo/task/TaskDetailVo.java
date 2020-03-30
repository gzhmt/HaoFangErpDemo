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
 * @Date: 2020/3/30 17:51
 * @Description:
 */
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDetailVo extends BaseTaskVo{
    /**
     * 生产任务编号
     */
    private String productionPlanNo;
    /**
     * 生产任务状态
     */
    private String taskNo;
    /**
     * 流水号
     */
    private String sequenceId;
    /**
     * 生产任务状态
     */
    private TaskStateEnum state;
    /**
     * 状态更改日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stateChangeDate;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private List<TaskProductGoodsVo> taskProductGoodsVos;

    private List<TaskMaterialGoodsVo> taskMaterialGoodsVos;
}