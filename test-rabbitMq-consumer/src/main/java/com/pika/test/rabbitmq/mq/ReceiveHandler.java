package com.pika.test.rabbitmq.mq;

import com.pika.test.rabbitmq.config.RabbitmqConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Pika
 * @create 2020/11/5
 * @description
 */
@Component
public class ReceiveHandler {
    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_EMAIL})
    public void send_email(String msg, Message message, Channel channel){
        System.out.println("receive message is:"+msg);
    }
}
