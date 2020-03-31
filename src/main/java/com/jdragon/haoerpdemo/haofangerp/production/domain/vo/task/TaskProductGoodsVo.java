package com.jdragon.haoerpdemo.haofangerp.production.domain.vo.task;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

/**
 * @Auther: kimid
 * @Date: 2020/3/30 17:06
 * @Description:
 */
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskProductGoodsVo extends TaskProductVo {
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