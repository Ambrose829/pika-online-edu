package com.pika.test.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Pika
 * @create 2020/11/4
 * @description 邮件路由消费者
 */
public class Consumer03_routing_email {
    //队列名称
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String EXCHANGE_ROUTING_INFORM="exchange_routing_inform";
    private static final String ROUTINGKEY_EMAIL="inform_email";

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
        try {
            connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();

            //监听队列
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
            channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);

            /**
             * 声明一个交换机
             * @param exchange 交换机的名称
             * @param type 交换机的类型
             *              fanout：对应的rabbitmq的工作模式是 publish/subscribe
             *              direct：对应的Routing	工作模式
             *              topic：对应的Topics工作模式
             *              headers： 对应的headers工作模式
             * @return
             */
            channel.exchangeDeclare(EXCHANGE_ROUTING_INFORM, BuiltinExchangeType.DIRECT);
            //交换机的绑定
            //queueBind(String queue, String exchange, String routingKey)
            /**
             *
             * @param queue 队列名称
             * @param exchange 交换机名称
             * @param routingKey 路由key，作用是交换机根据路由key的值将消息转发到指定的队列中，在发布订阅模式中协调为空字符串
             */
            channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_ROUTING_INFORM, ROUTINGKEY_EMAIL);
            //实现消费方法
            DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {

                /**
                 *
                 * @param consumerTag 消费者标签，用来标识消费者的，在监听队列时设置channel.basicConsume
                 * @param envelope 信封，通过envelope
                 * @param properties 消息属性
                 * @param body 消息内容
                 * @throws IOException
                 */
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    //交换机
                    String exchange = envelope.getExchange();
                    //消息id，mq在channel中用来标识消息的id，可用于确认消息已接收
                    long deliveryTag = envelope.getDeliveryTag();
                    //消息内容
                    String message = new String(body, "utf-8");
                    System.out.println("receive message" + message);
                }
            };

            /**
             * 监听队列
             * @param queue 队列名称
             * @param autoAck 自动回复，当消费者收到消息后要告诉mq消息已接收，如果将参数设置为true表示会自动回复mq，如果设置为false要通过变成实现回复
             * @param callback 消费方法，当消费者接收到消息要执行的方法
             */
            channel.basicConsume(QUEUE_INFORM_EMAIL,true, defaultConsumer);

        } catch (IOException e) {
            e.printStackTrace();

        } catch (
                TimeoutException e) {
            e.printStackTrace();

        }

    }
}
