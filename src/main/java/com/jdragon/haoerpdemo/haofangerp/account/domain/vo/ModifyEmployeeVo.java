package com.jdragon.haoerpdemo.haofangerp.account.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/5/13 下午1:17
 * @Description
 */
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModifyEmployeeVo {
    @Size(min = 2, max = 8, message = "名字需在2~8个字符之间")
    @Pattern(regexp = "^[^ \\n\\t]+$", message = "名字不能包含空字符串")
    private String name;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "出生日期不能为空")
    private Date birth;

    @Pattern(regexp = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$", message = "手机格式非法")
    @NotNull(message = "手机号不能为空")
    private String tel;

    @Pattern(regexp = "^[1-9]\\d{5}(18|19|20|(3\\d))\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$", message = "身份证格式非法")
    @NotNull(message = "身份证不能为空")
    private String idCard;
}
