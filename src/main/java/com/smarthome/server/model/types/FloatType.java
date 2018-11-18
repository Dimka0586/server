package com.smarthome.server.model.types;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("prototype")
public class FloatType  extends SimpleType<Float> {

    public FloatType() {
        super(0.0f);
        this.setName("float");
    }
}
