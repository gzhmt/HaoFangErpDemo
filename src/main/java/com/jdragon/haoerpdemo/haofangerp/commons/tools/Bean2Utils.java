package com.jdragon.haoerpdemo.haofangerp.commons.tools;

import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 20:22
 * @Description: BeanUtils的封装工具类
 */
public class Bean2Utils {
    @SneakyThrows
    public static Object copyProperties(Object source,Class<?> Class) {
        Object object = Class.newInstance();
        BeanUtils.copyProperties(source,object);
        return object;
    }
}
