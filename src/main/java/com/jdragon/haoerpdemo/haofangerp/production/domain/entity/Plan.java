package com.jdragon.haoerpdemo.haofangerp.production.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.EmergencyLevelEnum;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.PlanStateEnum;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.PlanStatusEnum;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.ToString;

import java.io.Serializable;

import java.util.Date;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 16:47
 * @Description: 生产计划实体类
 */

@Data
@ToString
@TableName("production_plan")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Plan extends Model<Plan>{
    @TableId(type = IdType.AUTO)
    private Long id;

    @Ignore
    private String productionNo;

    private PlanStateEnum state;

    private PlanStatusEnum status;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private int principalMember;

    private EmergencyLevelEnum emergencyLevel;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date closingDate;

    private int source;

    private String linkedOrder;

    private String remarks;

    @Override
    protected Serializable pkVal(){
        return this.id;
    }
}
