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
public class Plan extends Model<Plan> {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 创建人工号
     */
    private String createEmployeeNo;

    /**
     * 负责人工号
     */
    private String principalEmployeeNo;

    /**
     * 审批人工号
     */
    private String ApprovedEmployeeNo;

    /**
     * 生产单号
     */
    private String productionNo;

    /**
     * 状态
     */
    private PlanStateEnum state;

    /**
     * 状况
     */
    private PlanStatusEnum status;

    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /**
     * 审核状态
     */
    private PlanAuditStatusEnum auditStatus;

    /**
     * 紧急程度
     */
    private EmergencyLevelEnum emergencyLevel;

    /**
     * 截止日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date closingDate;

    /**
     * 来源
     */
    private String source;

    /**
     * 关联订单
     */
    private String linkedOrder;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 删除标记
     */
    @TableLogic(value = "0", delval = "1")
    private boolean deleted;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
