package com.jdragon.haoerpdemo.haofangerp.production.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.TaskStateEnum;
import lombok.Data;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
@TableName("production_task")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Task extends Model<Task>{
    @TableId(type = IdType.AUTO)
    private int id;

    private String taskNo;

    private String taskName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date productionDate;

    private int operatorMember;

    private TaskStateEnum state;

    private String remarks;

    private String productName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date forecastStartTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date forecastEndTime;

    private int productNumber;

    private int productionPlanId;

    private int sequenceId;
    @Override
    protected Serializable pkVal(){
        return this.id;
    }
}
