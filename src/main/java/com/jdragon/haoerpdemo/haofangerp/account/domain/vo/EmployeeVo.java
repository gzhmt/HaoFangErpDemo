package com.jdragon.haoerpdemo.haofangerp.account.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午4:33
 * @Description: 用户vo类
 */
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeVo {

    private String employeeNo;

    private String password;

    private String name;
    @ApiModelProperty(example = "1980-03-20 18:59:59")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birth;
    @ApiModelProperty(example = "2020-03-20 18:59:59")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date entryTime;
}
