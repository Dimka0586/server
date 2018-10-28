package com.smarthome.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Document(collection = "device_value_day")
@AllArgsConstructor
@Data
public class DeviceValueDay {
    private String _id;

    private String deviceId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date date;

    Map<Integer, Map<Integer, Map<Integer, Float>>> values;

    public DeviceValueDay() {
        this.date = new Date();
    }

    public DeviceValueDay(String deviceId, Float val) {
        this.deviceId = deviceId;
        this.values = new LinkedHashMap<>();
        Calendar cal = Calendar.getInstance();
        //cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        cal.setTime(new Date());
        cal.set(Calendar.MILLISECOND, 0);
        this.date = cal.getTime();
        Map<Integer, Map> hourMap = new LinkedHashMap<>();
        Map<Integer, Map<Integer, Float>> minuteMap = new LinkedHashMap<>();
        Map<Integer, Float> secondMap = new LinkedHashMap<>();

        secondMap.put(cal.get(Calendar.SECOND), val);
        minuteMap.put(cal.get(Calendar.MINUTE), secondMap);
        this.values.put(cal.get(Calendar.HOUR_OF_DAY), minuteMap);


    }

    public DeviceValueDay(Date date, Map<Integer, Map<Integer, Map<Integer, Float>>> values) {
        this.date = date;
        this.values = values;
    }
}
