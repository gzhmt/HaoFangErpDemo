package com.jdragon.haoerpdemo.haofangerp.commons.config;

import com.jdragon.haoerpdemo.haofangerp.commons.property.PathProperty;
import com.jdragon.haoerpdemo.haofangerp.commons.tools.SystemUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.05.13 21:29
 * @Description:
 */
@Configuration
@PropertySource("classpath:application.yml")
public class WebConfig implements WebMvcConfigurer {

    @Value("${project.windowsPath}")
    String windowsPath;

    @Value("${project.linuxPath}")
    String linuxPath;

    @Value("${project.avatarUrl}")
    String avatarUrl;

    @Value("${project.logoUrl}")
    String logoUrl;

    @Bean
    public PathProperty getPathProperty() {
        return new PathProperty(windowsPath, linuxPath, avatarUrl, logoUrl);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (SystemUtils.isWindows()) {
            registry.addResourceHandler(avatarUrl + "**").addResourceLocations("file:" + windowsPath + avatarUrl);
        } else {
            registry.addResourceHandler(avatarUrl + "**").addResourceLocations("file:" + linuxPath + avatarUrl);
        }
    }
}
