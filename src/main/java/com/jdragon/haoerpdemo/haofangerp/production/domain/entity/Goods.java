package com.jdragon.haoerpdemo.haofangerp.production.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Auther: kimid
 * @Date: 2020/3/27 09:35
 * @Description:
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("goods_info")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Goods  extends Model<Goods> {
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 货品编号
     */
    private String goodsNo;
    /**
     * 货品名称
     */
    private String goodsName;
    /**
     * 货品型号
     */
    private String goodsType;
    /**
     * 货品单位
     */
    private String goodsUnit;
    /**
     * 备注
     */
    private String remarks;
}