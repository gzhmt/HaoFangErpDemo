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
public class TaskDetailVo{
    /**
     * 唯一编号
     */
    private Long id;
    /**
     * 任务名称
     */
    private String taskName;
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
     * 操作员
     */
    private String operatorEmployeeNo;
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
    /**
     * 成品名称
     */
    private String productionName;
    /**
     * 成品数量
     */
    private String productionNumber;
    /**
     * 预计开始时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date forecastStartDate;
    /**
     * 预计结束时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date forecastEndDate;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 材料货品
     */
    private List<TaskMaterialGoodsVo> taskMaterialGoodsVos;
    /**
     * 成品货品
     */
    private  List<TaskProductGoodsVo> taskProductGoodsVos;
}