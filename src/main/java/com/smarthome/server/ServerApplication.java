package com.smarthome.server;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ServerApplication {

    @Autowired
    SimpleMessageListenerContainer simpleMessageListenerContainer;

    @Autowired
    TopicExchange amqTopic;

    @Autowired
    AmqpAdmin amqpAdmin;



    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @PostConstruct
    public void init() {
        /*List<String> queueList = new ArrayList<>();
        queueList.forEach(queue -> System.out.println(queue));
        Queue queue = new Queue("sensors_sensor4");
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(amqTopic)
                .with("sensors.sensor4"));
        queueList.add("sensors_sensor4");
        String[] queueArr = new String[queueList.size()];
        simpleMessageListenerContainer.setQueueNames(queueList.toArray(queueArr));*/

        List<String> queueList = new ArrayList<>();
        queueList.forEach(queue -> System.out.println(queue));
        Queue queue = new Queue("_tempBedroom");
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(amqTopic)
                .with("sensors.home.tempBedroom"));
        queueList.add("_tempBedroom");
        queue = new Queue("_tempBathroom");
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(amqTopic)
                .with("sensors.home.tempBathroom"));
        queueList.add("_tempBathroom");
        queue = new Queue("_tempKitchen");
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(amqTopic)
                .with("sensors.home.tempKitchen"));
        queueList.add("_tempKitchen");
        queue = new Queue("_tempKitchenFloor");
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(amqTopic)
                .with("sensors.home.tempKitchenFloor"));
        queueList.add("_tempKitchenFloor");
        String[] queueArr = new String[queueList.size()];
        simpleMessageListenerContainer.setQueueNames(queueList.toArray(queueArr));

    }
}
