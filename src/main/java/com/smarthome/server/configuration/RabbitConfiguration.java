package com.smarthome.server.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarthome.server.model.SimpleSensor;
import com.smarthome.server.service.DeviceValueDayService;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@EnableRabbit
@Configuration
public class RabbitConfiguration {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    DeviceValueDayService deviceValueDayService;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory("localhost");
        // connectionFactory.setUsername("home");
        // connectionFactory.setPassword("home");
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        //rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public TopicExchange amqTopic(){
        return new TopicExchange("amq.topic");
    }

    @Bean
    public SimpleMessageListenerContainer deviceListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                try {
                    //ObjectMapper objectMapper = new ObjectMapper();
                    //SimpleSensor simpleSensor = objectMapper.readValue(new String(message.getBody()), SimpleSensor.class);
                    // System.out.println(simpleSensor);
                    System.out.println(message.getMessageProperties().getReceivedRoutingKey() + ": " + new String(message.getBody()));
                    deviceValueDayService.addValueFromDevice("1", Float.parseFloat(new String(message.getBody())));


                }catch (Exception e) {
                }
            }
        });

        return container;
    }

}
