package com.pika.test.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Pika
 * @create 2020/11/4
 * @description rabbitmq入门程序
 */
//@SpringBootTest
//@RunWith(SpringRunner.class)
public class Producer01 {
    //队列
    public static final String QUEUE = "helloworld";
    public static void main(String[] args) {
        //通过连接工厂建立新的连接和mq连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //设置虚拟机，一个mq的服务可以设置多个虚拟机，每个虚拟机就相当于一个独立的mq
        connectionFactory.setVirtualHost("/");
        //建立新的连接
        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection();
            //创建会话通道，生产者和mq服务通信都在Channel中完成
            channel = connection.createChannel();
            //声明队列：如果队列在mq中没有则要创建
            //queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)

            /**
             * 参数明细
             * @param queue 队列名称
             * @param durable 是否持久化，如果持久化mq重启后队列还在
             * @param exclusive 是否独占连接，队列只允许在该连接中访问，如果连接关闭队列自动删除，如果将此参数设置为true可用于临时队列的创建
             * @param autoDelete 自动删除，队列不再使用时是否自动删除此队列，
             *                   如果将此参数和exclusive参数设置为true就可以实现临时队列（队列不用了就自动删除）
             * @param arguments 参数， 可以设置一个队列的扩展参数，比如设置存活时间
             */
            channel.queueDeclare(QUEUE, true, false, false, null);
            //发送消息，指定交换机
            //basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
            /**
             *
             * @param exchange 交换机，如果不指定将使用mq的默认交换机
             * @param routingKey 路由key，交换机根据路由key来将消息转发导致指定的队列，
             *                   如果使用默认交换机，routingkey设置为队列的名称
             * @param props 消息的属性
             * @param body 消息内容
             */
            //消息内容
            String message = "加油呀，你想要的都会有的";
            channel.basicPublish("", QUEUE, null, message.getBytes());

            System.out.println("send to mq" + message);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }

            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
