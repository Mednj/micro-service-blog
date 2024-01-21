package com.naja.analyticsmicroservice.kafka;
import com.naja.analyticsmicroservice.dependency.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class JsonKafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaConsumer.class);

    @KafkaListener(topics = "commentEventTopic", groupId = "comment-group")
    public void consumeComment(Comment comment) {
        LOGGER.info("Consumed comment message: {}", comment);

        // Add your logic to process the received comment
    }
}
