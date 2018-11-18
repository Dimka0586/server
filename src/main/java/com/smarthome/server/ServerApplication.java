package com.smarthome.server;

import com.smarthome.server.configuration.MqttTemplate;
import com.smarthome.server.model.Device;
import com.smarthome.server.model.DiscreteActuator;
import com.smarthome.server.model.types.*;
import com.smarthome.server.repository.CustomExchangeRepository;
import com.smarthome.server.repository.CustomInstanceRepository;
import com.smarthome.server.repository.CustomTypeRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    DeviceRepository deviceRepository;

    @Autowired
    NumberType numberType;

    @Autowired
    CustomTypeRepository customTypeRepository;

    @Autowired
    CustomInstanceRepository customInstanceRepository;

    @Autowired
    FloatType floatType;

    @Autowired
    IntegerType integerType;

    @Autowired
    StringType stringType;

    @Autowired
    BooleanType booleanType;

    @Autowired
    CustomExchangeRepository customExchangeRepository;

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    // @PostConstruct
    public void mqttInit() {
        System.out.println(numberType.getName());
        try {
            this.mqttClient.subscribe(new String[]{"/sensors/home/tempKitchen", "/sensors/home/tempKitchenFloor",
                    "/sensors/home/tempBedroom"});
        } catch (MqttException e) {
            e.printStackTrace();
        }
        Map<String, String> map = new HashMap<>();
        map.put("first", "value1");
        map.put("second", "value2");
        map.put("third", "value3");
        map.put("first", "value1");
        DiscreteActuator discreteActuator = new DiscreteActuator(false, map);
        this.mqttTemplate.convertAndSend("/controls/home/pumpKitchen", discreteActuator, 1);
    }
    // @PostConstruct
    public void testTypes() throws MqttException {

        customTypeRepository.deleteAll();
        customInstanceRepository.deleteAll();
        customExchangeRepository.deleteAll();


        Map<String, BaseType> scaleFields = new HashMap<>();
        scaleFields.put("minIn", floatType);
        scaleFields.put("maxIn", floatType);
        scaleFields.put("minOut", floatType);
        scaleFields.put("maxOut", floatType);

        // CustomType scale = CustomType.builder().fields(scaleFields).build();
        CustomType scale = new CustomType();
        scale.setFields(scaleFields);
        scale.buildPathValues("", scale.getFields());
        scale = customTypeRepository.save(scale);

        Map<String, BaseType> anSensorFields = new HashMap<>();
        anSensorFields.put("in", floatType);
        anSensorFields.put("scale", scale);

        // CustomType anSensor = CustomType.builder().fields(anSensorFields).build();
        CustomType anSensor = new CustomType();
        anSensor.setFields(anSensorFields);
        anSensor.buildPathValues("", anSensor.getFields());
        anSensor = customTypeRepository.save(anSensor);

        Map<String, BaseType> bundleAnSensorsFields = new HashMap<>();
        bundleAnSensorsFields.put("name", stringType);
        bundleAnSensorsFields.put("active", booleanType);
        bundleAnSensorsFields.put("temp", anSensor);
        bundleAnSensorsFields.put("hum", anSensor);

        // CustomType bundleAnSensors = CustomType.builder().fields(bundleAnSensorsFields).build();
        CustomType bundleAnSensors = new CustomType();
        bundleAnSensors.setFields(bundleAnSensorsFields);
        bundleAnSensors.buildPathValues("", bundleAnSensors.getFields());
        customTypeRepository.save(bundleAnSensors);

        CustomInstance tempHum1 = CustomInstance.builder().name("tempHum1").obj(bundleAnSensors).build();
        tempHum1 = customInstanceRepository.save(tempHum1);
        CustomExchange tempHum1Ex = CustomExchange.builder()
                .topic("/sensors/home/" + tempHum1.getName())
                .pathValues(tempHum1.getObj().getPathValues()).build();
        ((FloatType)tempHum1Ex.getPathValues().get("hum/in")).setValue(25.2f);
        tempHum1Ex = customExchangeRepository.save(tempHum1Ex);
        this.mqttClient.subscribe(tempHum1Ex.getTopic());
        this.mqttTemplate.convertAndSend(tempHum1Ex.getTopic(), tempHum1Ex, 1);




    }



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
// }
