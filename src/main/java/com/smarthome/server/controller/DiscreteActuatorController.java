package com.smarthome.server.controller;

import com.smarthome.server.configuration.MqttTemplate;
import com.smarthome.server.model.Device;
import com.smarthome.server.model.DiscreteActuator;
import com.smarthome.server.repository.DiscreteActuatorRepository;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController()
@RequestMapping("/discreteActuators")
public class DiscreteActuatorController {
    /*
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    SimpleMessageListenerContainer simpleMessageListenerContainer;

    @Autowired
    TopicExchange amqTopic;

    @Autowired
    AmqpAdmin amqpAdmin;*/

    @Autowired
    DiscreteActuatorRepository discreteActuatorRepository;

    @Autowired
    private MqttTemplate mqttTemplate;

    public DiscreteActuatorController() {

    }

    @GetMapping
    public List<DiscreteActuator> getDiscreteActuators() {
        List<DiscreteActuator> actuators = this.discreteActuatorRepository.findAll();
        return actuators;
    }

    @PostMapping
    public DiscreteActuator createDiscreteActuator(@RequestBody DiscreteActuator discreteActuator) {
        return this.discreteActuatorRepository.save(discreteActuator);
    }

    @PutMapping
    public DiscreteActuator updateDiscreteActuator(@RequestBody DiscreteActuator actuator) {
        actuator = this.discreteActuatorRepository.save(actuator);
        this.mqttTemplate.convertAndSend(actuator.getTopic(), actuator, 1);
        return actuator;
    }

    /*@PostMapping
    public DiscreteActuator createDiscreteActuator(@RequestBody DiscreteActuator discreteActuator) {
        *//*DiscreteActuator actuator = this.discreteActuatorRepository.save(discreteActuator);
        actuator.setQueueName(actuator.getName() + "_" + actuator.get_id());
        actuator = this.discreteActuatorRepository.save(actuator);
        this.addToBroker(actuator);
        return actuator;*//*
        DiscreteActuator actuator = this.discreteActuatorRepository.save(discreteActuator);
        actuator.setQueueName("mqtt-subscription-" + actuator.getBoardId() + "qos0");
        actuator = this.discreteActuatorRepository.save(actuator);
        this.addToBroker(actuator);
        return actuator;
    }

    @PutMapping
    public DiscreteActuator updateDiscreteActuator(@RequestBody DiscreteActuator actuator) {
        actuator = this.discreteActuatorRepository.save(actuator);
        this.rabbitTemplate.convertAndSend("amq.topic", "sensors.home." + actuator.getName(), actuator);
        return actuator;
    }

    private void addToBroker(DiscreteActuator actuator) {
        Queue queue = new Queue(actuator.getQueueName());
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(amqTopic).with("sensors.home." + actuator.getName()));
        simpleMessageListenerContainer.addQueueNames(actuator.getQueueName());
    }*/
}
