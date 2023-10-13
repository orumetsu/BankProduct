package com.multipolar.bootcamp.logging.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection = "access-log")
public class AccessLog {

    @Id
    private String id;

    private String requestMethod;
    private String requestUri;
    private Integer responseStatusCode;
    private String content;

    private LocalDateTime timeStamp = LocalDateTime.now();
}
