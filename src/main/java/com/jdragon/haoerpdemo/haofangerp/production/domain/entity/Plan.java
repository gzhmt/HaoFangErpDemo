package com.jdragon.haoerpdemo.haofangerp.production.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jdragon.haoerpdemo.haofangerp.production.constant.EmergencyLevelEnum;
import com.jdragon.haoerpdemo.haofangerp.production.constant.PlanAuditStatusEnum;
import com.jdragon.haoerpdemo.haofangerp.production.constant.PlanStateEnum;
import com.jdragon.haoerpdemo.haofangerp.production.constant.PlanStatusEnum;
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
 * @Description: 生产计划实体类
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("production_plan")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Plan extends Model<Plan>{
    @TableId(type = IdType.AUTO)
    private Long id;

    private String createEmployeeNo;

    private String principalEmployeeNo;

    private String productionNo;

    private PlanStateEnum state;

    private PlanStatusEnum status;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private PlanAuditStatusEnum auditStatus;

    private EmergencyLevelEnum emergencyLevel;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date closingDate;

    private String source;

    private String linkedOrder;

    private String remarks;

    @TableLogic(value = "1",delval = "0")
    private boolean activity;
    @Override
    protected Serializable pkVal(){
        return this.id;
    }
}
