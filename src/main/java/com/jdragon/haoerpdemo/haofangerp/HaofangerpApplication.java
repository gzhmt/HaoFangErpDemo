package com.jdragon.haoerpdemo.haofangerp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTransactionManagement
@EnableSwagger2
@EnableCaching
@CrossOrigin
@MapperScan(basePackages = {"com.jdragon.haoerpdemo.haofangerp.examine"},annotationClass = Repository.class)
public class HaofangerpApplication {

    public static void main(String[] args) {
        SpringApplication.run(HaofangerpApplication.class, args);
    }
}
