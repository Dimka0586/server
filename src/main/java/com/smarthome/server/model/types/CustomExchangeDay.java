package com.smarthome.server.model.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

@AllArgsConstructor
@Data
public class CustomExchangeDay {

    private String _id;

    private String ciId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date date;

    Map<Integer, Map<Integer, Map<Integer, CustomExchange>>> values;

    public CustomExchangeDay() {
        this.date = new Date();
    }

    public CustomExchangeDay(String ciId, CustomExchange val) {
        this.ciId = ciId;
        this.values = new LinkedHashMap<>();
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        cal.setTime(new Date());
        cal.set(Calendar.MILLISECOND, 0);
        this.date = cal.getTime();
        Map<Integer, Map> hourMap = new LinkedHashMap<>();
        Map<Integer, Map<Integer, CustomExchange>> minuteMap = new LinkedHashMap<>();
        Map<Integer, CustomExchange> secondMap = new LinkedHashMap<>();

        secondMap.put(cal.get(Calendar.SECOND), val);
        minuteMap.put(cal.get(Calendar.MINUTE), secondMap);
        this.values.put(cal.get(Calendar.HOUR_OF_DAY), minuteMap);


    }

    public CustomExchangeDay(Date date, Map<Integer, Map<Integer, Map<Integer, CustomExchange>>> values) {
        this.date = date;
        this.values = values;
    }
}
