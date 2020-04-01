package com.jdragon.haoerpdemo.haofangerp.examine.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jdragon.haoerpdemo.haofangerp.production.constant.TaskStateEnum;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.task.TaskMaterialGoodsVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.task.TaskProductGoodsVo;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author 金柏宇
 * @version 1.0
 * @date 2020/4/2 0:32
 */
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("production_task")
public class ExamineTaskDetailVo {
    /**
     * 唯一编号
     */
    private Long id;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 计划编号
     */
    private String productionPlanNo;
    /**
     * 生产任务编号
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
    @TableField(exist = false)//忽略MP映射
    private List<TaskMaterialGoodsVo> taskMaterialGoodsVos;
    /**
     * 成品货品
     */
    @TableField(exist = false)//忽略MP映射
    private  List<TaskProductGoodsVo> taskProductGoodsVos;
}