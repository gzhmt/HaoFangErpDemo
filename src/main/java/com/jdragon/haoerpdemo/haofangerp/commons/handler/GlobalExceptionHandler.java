package com.jdragon.haoerpdemo.haofangerp.commons.handler;

import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Auther: kimid
 * @Date: 2020/3/26 20:33
 * @Description: 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler  {

    @ExceptionHandler(value={MethodArgumentNotValidException.class})
    @ResponseBody
    public Result  getMessage(MethodArgumentNotValidException exception){
        // 获取NotNull注解中的message
        String message =  exception.getBindingResult().getFieldError().getDefaultMessage();
        return Result.error().setResult(message);
    }

}