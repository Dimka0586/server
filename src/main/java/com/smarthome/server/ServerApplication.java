package com.smarthome.server;

import com.smarthome.server.configuration.MqttTemplate;
import com.smarthome.server.model.Device;
import com.smarthome.server.model.DiscreteActuator;
import com.smarthome.server.repository.DeviceRepository;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
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

    /*@Autowired
    SimpleMessageListenerContainer simpleMessageListenerContainer;

    @Autowired
    TopicExchange amqTopic;

    @Autowired
    AmqpAdmin amqpAdmin;*/

    @Autowired
    MqttClient mqttClient;

    @Autowired
    MqttTemplate mqttTemplate;

    @Autowired
    private DeviceRepository deviceRepository;



    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @PostConstruct
    public void mqttInit() {
        try {
            this.mqttClient.subscribe(new String[]{"/sensors/home/tempKitchen", "/sensors/home/tempKitchenFloor",
                    "/sensors/home/tempBedroom"});
        } catch (MqttException e) {
            e.printStackTrace();
        }
        DiscreteActuator discreteActuator = new DiscreteActuator(false);
        this.mqttTemplate.convertAndSend("/controls/home/pumpKitchen", discreteActuator, 1);
    }



    //@PostConstruct
    /*public void init() {

        *//*List<Device> devices = this.deviceRepository.findAll();
        List<String> queueList = new ArrayList<>();
        Queue queue = null;
        for (int i = 0; i < devices.size(); i++) {
            queue = new Queue(devices.get(i).getQueueName());
            amqpAdmin.declareQueue(queue);
            amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(amqTopic).with("sensors.home." +
                    devices.get(i).getName()));
            queueList.add(devices.get(i).getQueueName());
        }
        String[] queueArr = new String[queueList.size()];
        simpleMessageListenerContainer.setQueueNames(queueList.toArray(queueArr));*//*

        List<String> queueList = new ArrayList<>();
        Queue queue = queue = new Queue("mqtt-subscription-arduino-homeqos0", false, false, false);

        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(amqTopic).with("sensors.home.vgd1"));
        amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(amqTopic).with("sensors.home.vgd2"));
        //queueList.add(queue.getName());
        //String[] queueArr = new String[queueList.size()];
        //simpleMessageListenerContainer.setQueueNames(queueList.toArray(queueArr));
        //simpleMessageListenerContainer.addQueueNames("mqtt-subscription-arduino-homeqos0");

    }*/
}
