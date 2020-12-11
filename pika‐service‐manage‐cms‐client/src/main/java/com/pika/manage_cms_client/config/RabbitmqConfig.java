package com.pika.manage_cms_client.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author Pika
 * @create 2020/11/5
 * @description rabbitmq配置类
 */
@Configuration
public class RabbitmqConfig {
    //队列bean的名称
    public static final String QUEUE_CMS_POSTPAGE = "queue_cms_postpage";
    //交换机的名称
    public static final String EX_ROUTING_CMS_POSTPAGE = "ex_routing_cms_postpage";
    //队列名称
    @Value("${pika.mq.queue}")
    public String queue_cms_postpage_name;
    //routingKey 即站点id
    @Value("${pika.mq.routingKey}")
    public String routingKey;

    //交换机配置，使用direct类型
    @Bean(EX_ROUTING_CMS_POSTPAGE)
    public Exchange EXCHANGE_ROUTING_INFORM() {
        return ExchangeBuilder.directExchange(EX_ROUTING_CMS_POSTPAGE).durable(true).build();
    }

    //声明队列
    @Bean(QUEUE_CMS_POSTPAGE)
    public Queue Queue_CMS_POSTPAGE() {
        Queue queue = new Queue(queue_cms_postpage_name);
        return queue;
    }

    @Bean
    public Binding BINDING_QUEUE_INFORM_SMS(
            @Qualifier(QUEUE_CMS_POSTPAGE) Queue queue, @Qualifier(EX_ROUTING_CMS_POSTPAGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }
}
