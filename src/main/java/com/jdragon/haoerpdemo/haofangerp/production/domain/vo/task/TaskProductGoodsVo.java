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

    private String goodsName;

    private String goodsType;

    private String goodsUnit;

    private String remarks;
}