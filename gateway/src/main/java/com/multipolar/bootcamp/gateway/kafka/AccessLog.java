package com.multipolar.bootcamp.gateway.kafka;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AccessLog implements Serializable {

    private String requestMethod;
    private String requestUri;
    private Integer responseStatusCode;
    private String content;
}
