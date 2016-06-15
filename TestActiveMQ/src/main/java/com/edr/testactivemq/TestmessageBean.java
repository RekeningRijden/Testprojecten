package com.edr.testactivemq;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;
import java.io.Serializable;

/**
 * Created by Eric on 14-06-16.
 */
@Named
@ViewScoped
public class TestmessageBean implements Serializable{

    @EJB
    private ListenerRabbitmq listener;

    @EJB
    private ListenerRabbitmqRouting listenerRouting;

    //Uncomment voor ActiveMq of ActiveMq Artemis
//    @EJB
//    private TestListener testListener;

    private String messageBody;

    private ProducerRabbitmq producerRMQ;


    @PostConstruct
    public void init() {
        try {

            producerRMQ = new ProducerRabbitmq();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage() {
        try {
            producerRMQ.sendMessage(messageBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}
