package com.jdragon.haoerpdemo.haofangerp.commons.tools;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/27 下午3:07
 * @Description 数据校验工具类
 */
public class ValidationUtils {
    /**
     * 检查数据绑定是否正确
     *
     * @param bindingResult 绑定结果对象
     * @return 若有绑定错误, 将返回错误信息
     */
    public static Map<String, String> checkBindingResult(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors != null && fieldErrors.size() > 0) {
            Map<String, String> map = new HashMap<>(fieldErrors.size());
            for (FieldError fieldError : fieldErrors) {
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            // 返回封装错误信息的Map
            return map;
        }
        return null;
    }
}
