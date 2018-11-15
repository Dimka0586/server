package com.smarthome.server.configuration;

import com.smarthome.server.service.DeviceValueDayService;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IMMqttCallback implements MqttCallback {

    //@Autowired
    //private MqttClient mqttClient;
    @Autowired
    private DeviceValueDayService deviceValueDayService;


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
    public void messageArrived(String topic, MqttMessage message) {
        System.out.println(topic + ": " + new String(message.getPayload()));
        deviceValueDayService.addValueFromDevice(topic,
                Float.parseFloat(new String(message.getPayload())));
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
