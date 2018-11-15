package com.smarthome.server.model.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomType extends BaseType {

    private Map<String, BaseType> fields;

}
