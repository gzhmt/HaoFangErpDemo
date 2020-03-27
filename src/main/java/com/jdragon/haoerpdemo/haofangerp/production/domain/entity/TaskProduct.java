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

import java.io.Serializable;

/**
 * @Auther: kimid
 * @Date: 2020/3/27 01:26
 * @Description:
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("production_task_product")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskProduct extends Model<TaskProduct> {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 任务编号
     */
    private String taskNo;
    /**
     * 成品编号
     */
    private String productNo;
    /**
     * 成品数量
     */
    private Integer productNumber;
    /**
     * 废品数量
     */
    private Integer scrapNumber;
    /**
     * 备注
     */
    private String remarks;

    @Override
    protected Serializable pkVal(){
        return this.id;
    }

}