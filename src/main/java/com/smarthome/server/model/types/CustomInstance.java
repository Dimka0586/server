package com.smarthome.server.model.types;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomInstance {

    private String _id;
    private String name;
    private CustomType obj;



}
