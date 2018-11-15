package com.smarthome.server.controller;

import com.smarthome.server.model.types.BaseType;
import com.smarthome.server.model.types.BooleanType;
import com.smarthome.server.model.types.NumberType;
import com.smarthome.server.model.types.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController()
@RequestMapping("/types")
public class TypeController {

    @Autowired
    NumberType numberType;

    @Autowired
    StringType stringType;

    @Autowired
    BooleanType booleanType;



    @GetMapping
    public List<BaseType> getTypes() {
        return Arrays.asList(numberType, stringType, booleanType);
    }
}
