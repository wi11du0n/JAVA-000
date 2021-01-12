package com.example.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.jms.*;

@SpringBootApplication
public class MqApplication {

    public static void main(String[] args) throws JMSException {
        ApplicationContext ac = SpringApplication.run(MqApplication.class, args);

        listenTopic();
        sendMessage();
        readMessage();
        System.exit(0);
    }

    private static void listenTopic() throws JMSException {

        ActiveMQConnectionFactory connectionFactory = getActiveMQConnectionFactory();

        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination topic = session.createTopic("TOPIC.BAR");

        MessageConsumer topicConsumer1 = session.createConsumer(topic);
        MessageConsumer topicConsumer2 = session.createConsumer(topic);

        topicConsumer1.setMessageListener(MqApplication::printMessage);
        topicConsumer2.setMessageListener(MqApplication::printMessage);
    }

    private static void readMessage() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = getActiveMQConnectionFactory();

        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("TEST.FOO");
        MessageConsumer consumer = session.createConsumer(destination);

        // Wait for a message
        Message message = consumer.receive(1000);
        printMessage(message);

        consumer.close();
        session.close();
        connection.close();

    }

    private static void printMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            String text = null;
            try {
                text = textMessage.getText();
            } catch (JMSException e) {
                e.printStackTrace();
            }
            System.out.println("Received: " + text);
        } else {
            System.out.println("Received: " + message);
        }
    }

    private static void sendMessage() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = getActiveMQConnectionFactory();

        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("TEST.FOO");
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        // Create a messages
        String text = "Hello world! From: " + Thread.currentThread().getName() + "-" + System.currentTimeMillis();
        TextMessage message = session.createTextMessage(text);

        // Tell the producer to send the message
        System.out.println("Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName());
        producer.send(message);

        Destination topic = session.createTopic("TOPIC.BAR");
        MessageProducer topicProducer =  session.createProducer(topic);
        topicProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        topicProducer.send(message);


        // Clean up
        session.close();
        connection.close();
    }

    private static ActiveMQConnectionFactory getActiveMQConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://localhost:61616"
        );
        return connectionFactory;
    }


}
