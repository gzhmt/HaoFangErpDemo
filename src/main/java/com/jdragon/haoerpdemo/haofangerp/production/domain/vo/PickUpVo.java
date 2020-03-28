package com.jdragon.haoerpdemo.haofangerp.production.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.TaskStateEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.26 16:01
 * @Description: 领用货品Vo
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PickUpVo {
    /**
     * 流水号
     */
    @ApiModelProperty(example = "LS-20200323-0001")
    private String sequenceId;

    /**
     * 申请时间
     */
    @ApiModelProperty(example = "2020-03-25 18:59:59")
    private Date applicationDate;

    /**
     * 仓库名
     */
    @ApiModelProperty(example = "测试仓库")
    private String warehouseName;

    /**
     * 要求到货时间
     */
    @ApiModelProperty(example = "2020-03-27 18:59:59")
    private Date requestedArrivalDate;

    /**
     * 是否还回
     */
    @ApiModelProperty(example = "true")
    private boolean payBack;

    /**
     * 到期提醒时间
     */
    @ApiModelProperty(example = "2020-03-27 17:59:59")
    private Date expirationReminderDate;

    /**
     * 申请人工号
     */
    @ApiModelProperty(example = "admin")
    private String applicantNo;

    /**
     * 状态
     */
    @ApiModelProperty(example = "已完成")
    private TaskStateEnum state;

    /**
     * 备注
     */
    @ApiModelProperty(example = "这是备注")
    private String remarks;
}
