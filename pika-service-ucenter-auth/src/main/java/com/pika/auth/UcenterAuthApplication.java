package com.pika.auth;

@EnableDiscoveryClient
@EnableFeignClients
@EntityScan("com.pika.framework.domain.ucenter")//扫描实体类
@ComponentScan(basePackages={"com.pika.api"})//扫描接口
@ComponentScan(basePackages={"com.pika.framework"})//扫描common下的所有类
@SpringBootApplication
public class UcenterAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterAuthApplication.class, args);
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }

}
