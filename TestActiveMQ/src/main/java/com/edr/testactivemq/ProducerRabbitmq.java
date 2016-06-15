package com.edr.testactivemq;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Eric on 15-06-16.
 */
public class ProducerRabbitmq {

    private final static String EXCHANGE_NAME = "exchange_routing";
    private final static String EXCHANGE_TYPE = "direct";

    private final static String QUEUE_NAME_BE = "belgie";
    private final static String QUEUE_NAME_NL = "nederland";

    private final static String ROUTING_KEY_BE = "BE";
    private final static String ROUTING_KEY_NL = "NL";


    private Channel channel;

    public ProducerRabbitmq() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.99.100");
        factory.setPort(5672);
        factory.setUsername("test");
        factory.setPassword("test");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);

        //Setup routing for RabbitMQ
        //Belgie
        channel.queueDeclare(QUEUE_NAME_BE, false, false, false, null);
        channel.queueBind(QUEUE_NAME_BE, EXCHANGE_NAME, ROUTING_KEY_BE);

        //Nederland
        channel.queueDeclare(QUEUE_NAME_NL, false, false, false, null);
        channel.queueBind(QUEUE_NAME_NL, EXCHANGE_NAME, ROUTING_KEY_NL);
    }

    public void sendMessage(String messagebody) throws Exception {
        if (channel != null) {
            String message = "This is a message for testing routing";
            channel.basicPublish(EXCHANGE_NAME, messagebody, null, message.getBytes());
            System.out.println("Sent message: " + message + ". With routing key: " + messagebody);
        } else {
            throw new Exception("channel is null");
        }
    }

}
