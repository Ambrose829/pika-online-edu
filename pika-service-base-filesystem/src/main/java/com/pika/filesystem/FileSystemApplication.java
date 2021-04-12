package com.pika.filesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//扫描所在包及子包的bean，注入到ioc中
@EntityScan("com.pika.framework.domain.filesystem")//扫描实体类
@ComponentScan(basePackages={"com.pika.api"})//扫描接口
@ComponentScan(basePackages={"com.pika.framework"})//扫描framework中通用类
@ComponentScan(basePackages={"com.pika.filesystem"})//扫描本项目下的所有类
public class FileSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileSystemApplication.class,args);
    }


}
