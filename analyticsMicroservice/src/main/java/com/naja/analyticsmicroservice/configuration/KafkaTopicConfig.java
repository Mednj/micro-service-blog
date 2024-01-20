package com.naja.analyticsmicroservice.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {


    // This is the topic that the orchestrator will send messages to for Format validation
    @Bean
    public NewTopic messageFormatTopic(){
        return TopicBuilder.name("commentEventTopic")
                .build();
    }

}
