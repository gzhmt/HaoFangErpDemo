package com.jdragon.haoerpdemo.haofangerp.production.domain.vo.task;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author kimid
 * @Date: 2020/3/29 21:19
 * @Description:添加任务Vo
 */
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskInsertVo extends BaseTaskVo {
    /**
     * 根据计划编号生成任务
     */
    @NotNull(message = "计划编号名称不能为空")
    @ApiModelProperty(example = "SC-20200326-0001")
    private String productionPlanNo;
//    @NotEmpty(message = "成品不能为空")
//    private List<TaskProductVo> taskProductVos;
//
//    @NotEmpty(message = "材料不能为空")
//    private List<TaskMaterialVo> taskMaterialVos;
}