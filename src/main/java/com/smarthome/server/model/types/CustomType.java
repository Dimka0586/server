package com.smarthome.server.model.types;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomType extends BaseType {

    private Map<String, BaseType> fields;
    private Map<String, SimpleType> pathValues = new HashMap<>();

    public void buildPathValues(String path, Map<String, BaseType> fields) {
        for (Map.Entry<String, BaseType> entry: fields.entrySet()) {
            String localpath = new String(path);
            if (entry.getValue() instanceof SimpleType) {
                localpath += (localpath.isEmpty()) ? entry.getKey() : "/" + entry.getKey();
                pathValues.put(localpath, (SimpleType) entry.getValue());
            } else if (entry.getValue() instanceof CustomType) {
                localpath += (localpath.isEmpty()) ? entry.getKey() : "/" + entry.getKey();
                buildPathValues(localpath, ((CustomType) entry.getValue()).fields);
            }
        }
    }

}
