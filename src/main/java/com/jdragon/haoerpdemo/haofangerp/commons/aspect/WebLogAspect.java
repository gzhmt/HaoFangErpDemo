package com.jdragon.haoerpdemo.haofangerp.commons.aspect;

/**
 * @Auther: kimid
 * @Date: 2020/3/26 21:14
 * @Description:
 */

import com.jdragon.haoerpdemo.haofangerp.security.commons.SecurityContextHolderHelper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @Author kimi
 * @Description:apo日志打印
 * @Date 2020/3/25 21:41
 **/
@Aspect
@Component
@Slf4j
public class WebLogAspect {

    public WebLogAspect() {
    }

    /**
     * @Author kimi
     * @Description:定义请求日志切入点
     * @Date 2020/3/25 21:41
     * @Param []
     * @return void
     **/
    @Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
    public void webLogPointcut() {
    }

    /**
     * @Author kimi
     * @Description:记录异常日志
     * @Date 2020/3/25 21:39
     * @Param [throwable]
     * @return void
     **/
    @AfterThrowing(value = "webLogPointcut()", throwing = "throwable")
    public void doAfterThrowing(Throwable throwable) {
        log.error("发生异常时间：", LocalDateTime.now());
        log.error("抛出异常：", throwable.getMessage());
    }

    /**
     * @Author kimi
     * @Description:记录访问日志
     * @Date 2020/3/26 21:40
     * @Param [joinPoint]
     * @throws Throwable
     * @return java.lang.Object
     **/
    @Around("webLogPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ApiOperation annotation = method.getAnnotation(ApiOperation.class);
        if (annotation != null) {
            String employeeNo = SecurityContextHolderHelper.getEmployeeNo();
            log.info("操作人："+employeeNo);
            log.info("执行方法："+method.getName());
            log.info("描述："+annotation.value());
        }
        return joinPoint.proceed();
    }
}