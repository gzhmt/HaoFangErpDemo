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
@ToString
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result implements Serializable {

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

    public Result(ResultCode resultCode,Object result){
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.result = result;
    }

    public static Result success(){
        return success(null);
    }
    public static Result success(Object result) {
        return new Result(ResultCode.NORMAL, result);
    }
    public static Result error(){
        return error(null);
    }
    public static Result error(Object result) {
        return new Result(ResultCode.SYSTEM_ERROR, result);
    }

    public static Result authFail(Object result){
        return new Result(ResultCode.AUTH_FAIL,result);
    }

    public static Result permissionsNotEnough(Object result) {
        return new Result(ResultCode.PERMISSIONS_NOT_ENOUGH,result);
    }

    public static Result paramsError(Object result) {
        return new Result(ResultCode.PARAMS_ERROR, result);
    }
    public static Result unKnowError(Object result){
        return new Result(ResultCode.SYSTEM_UN_KNOW_ERROR,result);
    }
    /**
     * 判断是否成功： 依据 Wrapper.SUCCESS_CODE == this.code
     *
     * @return code =200,true;否则 false.
     */
    @JsonIgnore
    public boolean result() {
        return ResultCode.NORMAL.getCode().equals(code);
    }
}
