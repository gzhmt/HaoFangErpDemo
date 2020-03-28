package com.jdragon.haoerpdemo.haofangerp.account.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/28 下午2:01
 * @Description
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("system_role_power")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RolePower {
    /**
     * 唯一标识
     */
    @TableId(type = IdType.AUTO)
    private int id;

    /**
     * 权限id
     */
    private int powerId;

    /**
     * 角色id
     */
    private int roleId;

}
