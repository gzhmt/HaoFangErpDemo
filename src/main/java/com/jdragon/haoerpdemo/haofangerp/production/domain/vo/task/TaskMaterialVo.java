package com.jdragon.haoerpdemo.haofangerp.production.domain.vo.task;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @Auther: kimid
 * @Date: 2020/3/27 01:26
 * @Description:
 */
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskMaterialVo{

//    @ApiModelProperty(hidden = true)
//    private Long id;
//    /**
//     * 任务编号
//     */
//    @ApiModelProperty(hidden = true)
//    private String taskNo;
    /**
     * 材料编号
     */
    @NotNull(message = "材料编号不能为空")
    @ApiModelProperty(example = "SPB002")
    private String materialNo;
    /**
     * 材料数量
     */
    @ApiModelProperty(example = "0")
    private Integer materialNumber;
    /**
     * 废品数量
     */
    @ApiModelProperty(example = "0")
    private Integer scrapNumber;
    /**
     * 备注
     */
    @ApiModelProperty(example = "备注")
    private String remarks;

}