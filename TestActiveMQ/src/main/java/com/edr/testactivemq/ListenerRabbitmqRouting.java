package com.edr.testactivemq;

import com.rabbitmq.client.*;

import javax.ejb.Stateless;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Eric on 15-06-16.
 */
@Stateless
public class ListenerRabbitmqRouting {
    private final static String QUEUE_NAME = "nederland";
    private final static String EXCHANGE_NAME = "exchange_routing";
    private final static String ROUTING_KEY = "NL";

    public ListenerRabbitmqRouting() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("test");
        factory.setPassword("test");
        factory.setHost("192.168.99.100");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("Waiting for messages on queue: goodbye");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Received message: " + message + " on queue " + QUEUE_NAME + " with routing key: " + ROUTING_KEY);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
