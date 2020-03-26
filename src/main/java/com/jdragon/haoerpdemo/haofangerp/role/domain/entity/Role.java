package com.jdragon.haoerpdemo.haofangerp.role.domain.entity;

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
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午3:30
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("system_role")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role extends Model<Role> {

    @TableId(type = IdType.AUTO)
    private int id;

    private String roleName;

    private int roleSort;

    private String roleDescribe;

    @Override
    protected Serializable pkVal(){
        return this.id;
    }
}
