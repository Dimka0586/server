package com.smarthome.server.service;

import com.smarthome.server.model.DeviceValueDay;
import com.smarthome.server.repository.DeviceValueDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DeviceValueDayServiceImpl implements DeviceValueDayService {

    @Autowired
    DeviceValueDayRepository deviceValueDayRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void addValueFromDevice(String deviceId, Float value) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, 1);
        Date before = calendar.getTime();
        calendar.add(Calendar.DATE, -2);
        Date after = calendar.getTime();
        calendar.setTime(date);

        Query query = new Query();
        query.addCriteria(Criteria.where("date").lt(before).gt(after)
                .andOperator(Criteria.where("deviceId").is(deviceId)));
        DeviceValueDay deviceValueDay = mongoTemplate.findOne(query, DeviceValueDay.class);

        if (deviceValueDay == null) {
            deviceValueDay = deviceValueDayRepository.save(new DeviceValueDay(deviceId, value));
        } else {
            Map<Integer, Map<Integer, Map<Integer, Float>>> values = deviceValueDay.getValues();
            Map<Integer, Map<Integer, Float>> minuteMap = values.get(calendar.get(Calendar.HOUR_OF_DAY));
            Map<Integer, Float> secondMap = new LinkedHashMap<>();

            if (minuteMap == null) {
                secondMap.put(calendar.get(Calendar.SECOND), value);
                minuteMap = new LinkedHashMap<>();
                minuteMap.put(calendar.get(Calendar.MINUTE), secondMap);
                values.put(calendar.get(Calendar.HOUR_OF_DAY), minuteMap);
                deviceValueDay.setValues(values);
            } else {
                secondMap = minuteMap.get(calendar.get(Calendar.MINUTE));
                if (secondMap == null) {
                    secondMap = new LinkedHashMap<>();


                }
                secondMap.put(calendar.get(Calendar.SECOND), value);
                minuteMap.put(calendar.get(Calendar.MINUTE), secondMap);
            }
        }
        mongoTemplate.save(deviceValueDay);
    }
}
