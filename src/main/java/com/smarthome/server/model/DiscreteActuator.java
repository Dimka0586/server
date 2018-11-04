package com.smarthome.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DiscreteActuator extends Device {
    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    //private Date date;

    private Boolean on;



}
