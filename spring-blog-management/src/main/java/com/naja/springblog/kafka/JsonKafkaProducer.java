package com.naja.analyticsmicroservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class JsonKafkaProducer
{
    //this is the JsonKafkaProducer responsible for sending the message to the JsonMessageFormat2 topic
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaProducer.class);

    // the message will be sent as a FinancialMessageEntity object
    private KafkaTemplate<String, FinancialMessageEntity> kafkaTemplate;

    public JsonKafkaProducer(KafkaTemplate<String, FinancialMessageEntity> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(FinancialMessageEntity data){
        LOGGER.info("Producing message: {}",data);
        Message<FinancialMessageEntity> message = MessageBuilder.withPayload(data).setHeader(
                KafkaHeaders.TOPIC, "JsonMessageFormat2"
        ).build();

        kafkaTemplate.send(message);
    }
}
