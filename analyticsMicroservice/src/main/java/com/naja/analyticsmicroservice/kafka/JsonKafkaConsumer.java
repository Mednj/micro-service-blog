package com.naja.integrity_check_orchestrator.kafka;

import com.naja.ValidationResult;
import com.naja.dependency.FinancialMessageEntity;
import com.naja.integrity_check_orchestrator.model.ValidFinancialTransactionEntity;
import com.naja.integrity_check_orchestrator.repository.FinancialMessageEntityRepository;
import com.naja.integrity_check_orchestrator.repository.ValidFinancialTransactionEntiryRepository;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class JsonKafkaConsumer {

    private final FinancialMessageEntityRepository financialMessageEntityRepository;
    private final ValidFinancialTransactionEntiryRepository validFinancialTransactionEntiryRepository;

    //this is the JsonKafkaConsumer responsible for consuming the message from the JsonMessageFormatResult topic
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(JsonKafkaConsumer.class);

    public JsonKafkaConsumer(FinancialMessageEntityRepository financialMessageEntityRepository, ValidFinancialTransactionEntiryRepository validFinancialTransactionEntiryRepository) {
        this.financialMessageEntityRepository = financialMessageEntityRepository;
        this.validFinancialTransactionEntiryRepository = validFinancialTransactionEntiryRepository;
    }

    @KafkaListener(topics = "JsonMessageFormatResult2", groupId = "group_id2")
    public void consume(ValidationResult message) {
        LOGGER.info("Consumed message: {}", message.toString());
        FinancialMessageEntity financialMessage =financialMessageEntityRepository.findById(message.getId());
        LOGGER.info("the class recuperated from the database coressponding : " + financialMessage);
        if(message.isValid()){
            ValidFinancialTransactionEntity validFinancialTransaction = (ValidFinancialTransactionEntity) financialMessage;
            validFinancialTransactionEntiryRepository.save(validFinancialTransaction);
        }




    }
}
