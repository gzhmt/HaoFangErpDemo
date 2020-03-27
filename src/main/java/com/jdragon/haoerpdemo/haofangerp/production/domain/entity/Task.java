package com.jdragon.haoerpdemo.haofangerp.production.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.TaskStateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 16:47
 * @Description: 生产任务实体类
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("production_task")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Task extends Model<Task>{
    /**
     * 唯一标识
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 任务编号
     */
    private String taskNo;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 流水号
     */
    private String sequenceId;
    /**
     * 生产计划编号
     */
    private String productionPlanNo;

    /**
     * 操作人员
     */
    private String operatorEmployeeNo;
    /**
     * 任务状态
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
     * 预计开始日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date forecastStartDate;
    /**
     * 预计结束日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date forecastEndDate;
    /**
     * 备注
     */
    private String remarks;


    @Override
    protected Serializable pkVal(){
        return this.id;
    }
}
