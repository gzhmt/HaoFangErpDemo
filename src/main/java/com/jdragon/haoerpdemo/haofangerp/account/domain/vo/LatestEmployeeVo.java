package com.jdragon.haoerpdemo.haofangerp.account.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @date 2020/5/13 下午12:05
 * @Description
 */
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LatestEmployeeVo {

    private String employeeNo;

    private String employeeName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birth;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date entryTime;

    private String departmentName;

    private String tel;

    private String idCard;

    private String photoUrl;
}
