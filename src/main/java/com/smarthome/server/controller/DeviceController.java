package com.smarthome.server.controller;

import com.smarthome.server.model.Device;
import com.smarthome.server.repository.DeviceRepository;
import lombok.NoArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/devices")
public class DeviceController {
    /*
    @Autowired
    SimpleMessageListenerContainer simpleMessageListenerContainer;

    @Autowired
    TopicExchange amqTopic;

    @Autowired
    AmqpAdmin amqpAdmin;*/

    @Autowired
    private DeviceRepository deviceRepository;

    public DeviceController() {

    }

    @GetMapping
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    @PostMapping
    public Device createDevice(@RequestBody Device device) {
        return null;
    }




    /*@PostMapping
    public Device createDevice(@RequestBody Device device) {
        Device created = this.deviceRepository.save(device);
        created.setQueueName(created.getName() + "_" + created.get_id());
        created = this.deviceRepository.save(created);
        this.addToBroker(created);
        return created;
    }

    private void addToBroker(Device device) {
        Queue queue = new Queue(device.getQueueName());
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(amqTopic).with("sensors.home." + device.getName()));
        simpleMessageListenerContainer.addQueueNames(device.getQueueName());
    }*/

}
