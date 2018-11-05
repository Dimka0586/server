package com.smarthome.server.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class MqttTemplate {

    @Autowired
    private MqttClient mqttClient;

    @Autowired
    private ObjectMapper objectMapper;

    public void convertAndSend(String topic, Object content, int qos) {
        MqttMessage message = null;
        try {
            message = new MqttMessage(objectMapper.writeValueAsString(content).getBytes());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        message.setQos(qos);
        try {
            mqttClient.publish(topic, message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }



}
