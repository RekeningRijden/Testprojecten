package com.edr.testactivemq;

//import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.NamingException;

/**
 * Created by Eric on 14-06-16.
 */
/**
 * Dependencies zijn al toegevoegd in pom.xml
 * Voor Artemis: gebruik "import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;"
 * Voor ActiveMq: gebruik: "import org.apache.activemq.ActiveMQConnectionFactory;"
 */

public class Producer {

    private Queue queue;
    private Session session;

    public Producer() throws NamingException, JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        queue = session.createQueue("test");
    }

    public void sendMessage(String body) throws JMSException {
        MessageProducer producer = session.createProducer(queue);

        Message message = session.createMessage();
        message.setStringProperty("body", body);

        System.out.println("Sent message: " + message.getStringProperty("body"));

        producer.send(message);
    }
}
