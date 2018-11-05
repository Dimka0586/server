package com.smarthome.server.configuration;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttV3Config {

    @Autowired
    private MqttCallback mqttCallback;

    @Bean
    MqttClient mqttClient() throws MqttException {
        MqttClient mqttClient = new MqttClient("tcp://127.0.0.1:1883", "rpi-server1");
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        mqttClient.setCallback(this.mqttCallback);
        mqttClient.connect(connOpts);
        return mqttClient;
    }




}
