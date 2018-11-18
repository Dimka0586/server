package com.smarthome.server.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarthome.server.model.types.CustomExchange;
import com.smarthome.server.model.types.CustomType;
import com.smarthome.server.repository.CustomExchangeRepository;
import com.smarthome.server.service.CustomExchangeService;
import com.smarthome.server.service.DeviceValueDayService;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class IMMqttCallback implements MqttCallback {

    @Autowired
    private DeviceValueDayService deviceValueDayService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CustomExchangeRepository customExchangeRepository;

    @Autowired
    CustomExchangeService customExchangeService;


    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost on instance \""
                + "\" with cause \"" + cause.getMessage() + "\" Reason code "
                + ((MqttException)cause).getReasonCode() + "\" Cause \""
                + ((MqttException)cause).getCause() +  "\"");
        cause.printStackTrace();

    }

    /* (non-Javadoc)
     * @see org.eclipse.paho.client.mqttv3.MqttCallback#messageArrived(java.lang.String, org.eclipse.paho.client.mqttv3.MqttMessage)
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws IOException {
        System.out.println(topic + ": " + new String(message.getPayload()));
        CustomExchange customExchange = objectMapper.readValue(message.getPayload(), CustomExchange.class);
        System.out.println(customExchange);
        customExchangeService.customExchangeStore(customExchange);

        // customExchangeRepository.save(customExchange);
        // deviceValueDayService.addValueFromDevice(topic, customExchange);

        // deviceValueDayService.addValueFromDevice(topic,
        //    Float.parseFloat(new String(message.getPayload())));
        /*redisTemplate.opsForList().leftPush(
                MqttClientFactory.redisArrivedPrefix.concat(mqttClient.getClientId()),
                topic.concat(";").concat(message.toString()));
        TopicsHandlesMap.getHandle(topic).handle(mqttClient,topic, message);*/
    }

    /* (non-Javadoc)
     * @see org.eclipse.paho.client.mqttv3.MqttCallback#deliveryComplete(org.eclipse.paho.client.mqttv3.IMqttDeliveryToken)
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        try {
            System.out.println("Delivery token \"" + token.hashCode()
                    + "\" received by instance \""  + "\"");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
