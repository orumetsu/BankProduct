package com.multipolar.bootcamp.logging.repository;

import com.multipolar.bootcamp.logging.domain.AccessLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccessLogRepository extends MongoRepository<AccessLog, String> {
}
