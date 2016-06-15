package com.edr.testactivemq;


//import org.apache.activemq.ActiveMQConnectionFactory;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.ejb.Stateless;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;

/**
 * Created by Eric on 14-06-16.
 */
//Uncomment bij gebruik
//@Stateless
/**
 * Dependencies zijn al toegevoegd in pom.xml
 * Voor Artemis: gebruik "import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;"
 * Voor ActiveMq: gebruik: "import org.apache.activemq.ActiveMQConnectionFactory;"
 */

public class TestListener implements Serializable {

    public TestListener() throws JMSException, NamingException {

        //Empty constructor
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("test");
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    System.out.println("Received message: " + message.getStringProperty("body"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
