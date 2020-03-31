package com.jdragon.haoerpdemo.haofangerp.production.domain.vo.task;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @Auther: kimid
 * @Date: 2020/3/30 17:06
 * @Description:
 */
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskMaterialGoodsVo extends TaskMaterialVo {

    /**
     * 货品名称
     */
    private String goodsName;
    /**
     * 货品类型
     */
    private String goodsType;
    /**
     * 或品单位
     */
    private String goodsUnit;
    /**
     * 备注
     */
    private String remarks;
}