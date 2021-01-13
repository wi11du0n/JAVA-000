package com.example.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.jms.*;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@SpringBootApplication
public class MqApplication {

    public static void main(String[] args) throws JMSException {
        ApplicationContext ac = SpringApplication.run(MqApplication.class, args);

        listenTopic();
        sendMessage();
        readMessage();

        kafkaProducer();
        kafkaConsumer();
        System.exit(0);
    }

    private static void kafkaProducer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9001,localhost:9002,localhost:9003");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        ProducerRecord record;
        for (int i = 0; i < 10000; i++) {
            record = new ProducerRecord("test32", "message" + i);
            producer.send(record);
        }
    }

    private static void kafkaConsumer() {
        Properties properties = new Properties();
        properties.put("group.id", "gaol");
        properties.put("bootstrap.servers", "localhost:9001,localhost:9002,localhost:9003");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer consumer = new KafkaConsumer(properties);
        consumer.subscribe(Collections.singletonList("test32"));
        while (true) {
            ConsumerRecords<String, String> poll = consumer.poll(Duration.ofMillis(500L));
            for (ConsumerRecord<String, String> stringStringConsumerRecord : poll) {
                System.out.println(stringStringConsumerRecord.value());
            }
        }
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
