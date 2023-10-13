package com.multipolar.bootcamp.logging.service;

import com.multipolar.bootcamp.logging.domain.AccessLog;
import com.multipolar.bootcamp.logging.repository.AccessLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaConsumerService {

    private final ObjectMapper objectMapper;
    private final AccessLogRepository accessLogRepository;

    @Autowired
    public KafkaConsumerService(ObjectMapper objectMapper, AccessLogRepository accessLogRepository) {
        this.objectMapper = objectMapper;
        this.accessLogRepository = accessLogRepository;
    }

    @KafkaListener(topics = "product-access-logs")
    public void receiveMessage(String message) {
        try {
            // Deserialize the JSON message into an AccessLog object
            AccessLog accessLog = objectMapper.readValue(message, AccessLog.class);
            System.out.println("Received message: " + accessLog);
            accessLogRepository.insert(accessLog);
        } catch (Exception e) {
            // Handle any exceptions that may occur during deserialization
            System.err.println("Error processing message: " + e.getMessage());
        }
    }
}

