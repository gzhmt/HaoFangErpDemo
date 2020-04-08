package com.jdragon.haoerpdemo.haofangerp.commons.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 17:45
 * @Description: 返回结果构造类
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result implements Serializable {

    /**
     * 成功码.
     */
    public static final Long SUCCESS_CODE = ResultCode.NORMAL.getCode();

    /**
     * 成功信息.
     */
    public static final String SUCCESS_MESSAGE = ResultCode.NORMAL.getMessage();

    /**
     * 错误码.
     */
    public static final Long ERROR_CODE = ResultCode.SYSTEM_ERROR.getCode();

    /**
     * 错误信息.
     */
    public static final String ERROR_MESSAGE = ResultCode.SYSTEM_ERROR.getMessage();

    /**
     * 错误码：参数非法
     */
    public static final Long ILLEGAL_ARGUMENT_CODE_ = ResultCode.PARAMS_ERROR.getCode();

    /**
     * 错误信息：参数非法
     */
    public static final String ILLEGAL_ARGUMENT_MESSAGE = ResultCode.PARAMS_ERROR.getMessage();

    /**
     * 错误码：认证失败
     */
    public static final Long AUTH_FAIL_CODE = ResultCode.AUTH_FAIL.getCode();

    /**
     * 错误信息：认证失败
     */
    public static final String AUTH_FAIL_MESSAGE = ResultCode.AUTH_FAIL.getMessage();

    /**
     * 编号.
     */
    private Long code;

    /**
     * 信息.
     */
    private String message;

    /**
     * 结果数据
     */
    private Object result;

    public Result(Long code, String message) {
        this(code, message, null);
    }

    public static Result success(){
        return success(SUCCESS_MESSAGE);
    }
    public static Result success(String message) {
        return new Result(SUCCESS_CODE, message, null);
    }
    public static Result error(){
        return error(ERROR_MESSAGE);
    }
    public static Result error(String message) {
        return new Result(ERROR_CODE, message, null);
    }

    public static Result error(ResultCode resultCode){
        return new Result(resultCode.getCode(),resultCode.getMessage(),null);
    }



    public static Result permissionsNotEnough(String message) {
        return error(ResultCode.PERMISSIONS_NOT_ENOUGH).setResult(message);
    }

    public static Result authFail(String message){
        return new Result(AUTH_FAIL_CODE, message, null);
    }

    public static Result authFail(){
        return authFail( AUTH_FAIL_MESSAGE);
    }


    public static Result paramsError(String message) {
        return new Result(ILLEGAL_ARGUMENT_CODE_, message, null);
    }

    /**
     * 判断是否成功： 依据 Wrapper.SUCCESS_CODE == this.code
     *
     * @return code =200,true;否则 false.
     */
    @JsonIgnore
    public boolean result() {
        return Result.SUCCESS_CODE == code;
    }
}
