package com.smarthome.server.model.types;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("prototype")
public class StringType extends SimpleType<String> {

    public StringType() {
        super("");
        this.setName("string");
    }
}
