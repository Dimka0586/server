package com.smarthome.server.model.types;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomExchange {

    private String ciId;
    private String topic;
    private Map<String, SimpleType> pathValues;
}
