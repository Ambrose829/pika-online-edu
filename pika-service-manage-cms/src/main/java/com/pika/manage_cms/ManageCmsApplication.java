package com.pika.manage_cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Pika
 * @create 2020/10/29
 * @description
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EntityScan("com.pika.framework.domain.cms") //扫描实体类
@ComponentScan(basePackages = "com.pika.api")//扫描接口
@ComponentScan(basePackages = "com.pika.manage_cms") //扫描本项目下的包，可以不写
@ComponentScan(basePackages = "com.pika.framework") //扫描common下面的包，保证我们自定义的异常捕获器可以正常运行
public class ManageCmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsApplication.class, args);
    }
}
