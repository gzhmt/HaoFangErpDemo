package com.jdragon.haoerpdemo.haofangerp.account.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/27 下午2:55
 * @Description 角色vo类
 */
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleVo {

    @NotNull(message = "请求参数不能为空")
    private String roleName;

    @NotNull(message = "请求参数不能为空")
    private int roleSort;

    @NotNull(message = "请求参数不能为空")
    private String roleDescribe;
}
