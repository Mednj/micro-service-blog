package com.naja.integrity_check_orchestrator.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {


    // This is the topic that the orchestrator will send messages to for Format validation
    @Bean
    public NewTopic messageFormatTopic(){
        return TopicBuilder.name("JsonMessageFormat2")
                .build();
    }

    // This is the topic where the orchestrator will receive messages from the Format validation service
    @Bean
    public NewTopic messageFormatResponseTopic(){
        return TopicBuilder.name("JsonMessageFormatResult2")
                .build();
    }
}
