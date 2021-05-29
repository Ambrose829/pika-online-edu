package com.pika.manage_course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Pika
 * @create 2020/11/8
 * @description
 */
@SpringBootApplication
@EntityScan("com.pika.framework.domain.course")//扫描实体类
@EnableFeignClients(basePackages = {"com.pika.manage_course"})
@ComponentScan(basePackages={"com.pika.api"})//扫描接口
@ComponentScan(basePackages={"com.pika.manage_course"})
@ComponentScan(basePackages={"com.pika.framework"})//扫描common下的所有类
public class ManageCourseApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCourseApplication.class, args);

    }
}
