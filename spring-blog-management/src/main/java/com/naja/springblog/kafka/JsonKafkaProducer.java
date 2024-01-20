package com.naja.springblog.kafka;

import com.naja.springblog.model.Comment;
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
    private final KafkaTemplate<String, Comment> kafkaTemplate;

    public JsonKafkaProducer(KafkaTemplate<String, Comment> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Comment comment) {
        LOGGER.info("Producing comment message: {}", comment);

        Message<Comment> message = MessageBuilder
                .withPayload(comment)
                .setHeader(KafkaHeaders.TOPIC, "commentEventTopic")
                .build();

        kafkaTemplate.send(message);
    }


}
