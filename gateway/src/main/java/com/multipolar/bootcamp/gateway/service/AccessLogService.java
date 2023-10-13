package com.multipolar.bootcamp.gateway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.multipolar.bootcamp.gateway.kafka.AccessLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccessLogService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public AccessLogService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void logAccess(AccessLog accessLog) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String accessLogMessage = objectMapper.writeValueAsString(accessLog);
            kafkaTemplate.send("product-access-logs", accessLogMessage);
        } catch (KafkaException | JsonProcessingException e) {
            AccessLog log  = new AccessLog();
            log.setContent(e.getLocalizedMessage());
            kafkaTemplate.send("product-access-logs", log.toString());
        }
    }
}