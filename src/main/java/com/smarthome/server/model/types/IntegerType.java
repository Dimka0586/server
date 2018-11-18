package com.smarthome.server.model.types;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("prototype")
public class IntegerType  extends SimpleType<Integer> {

    public IntegerType() {
        super(0);
        this.setName("integer");
    }
}
