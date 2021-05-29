package com.pika.manage_cms_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Pika
 * @create 2020/11/5
 * @description
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EntityScan("com.pika.framework.domain.cms") //扫描实体类
@ComponentScan(basePackages = "com.pika.manage_cms_client") //扫描本项目下的包，可以不写
@ComponentScan(basePackages = "com.pika.framework") //扫描common下面的包，保证我们自定义的异常捕获器可以正常运行
public class ManageCmsClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsClientApplication.class, args);

    }
}
