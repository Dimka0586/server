package com.smarthome.server.service;

import com.smarthome.server.model.types.CustomExchange;
import com.smarthome.server.model.types.CustomExchangeDay;
import com.smarthome.server.repository.CustomExchangeDayRepository;
import com.smarthome.server.repository.CustomExchangeRepository;
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
public class CustomExchangeService {

    @Autowired
    CustomExchangeRepository customExchangeRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    CustomExchangeDayRepository customExchangeDayRepository;

    public void customExchangeStore(CustomExchange customExchange) {
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
                .andOperator(Criteria.where("ciId").is(customExchange.getCiId())));
        CustomExchangeDay customExchangeDay = mongoTemplate.findOne(query, CustomExchangeDay.class);

        if (customExchangeDay == null) {
            customExchangeDay = customExchangeDayRepository.save(new CustomExchangeDay(customExchange.getCiId(), customExchange));
        } else {
            Map<Integer, Map<Integer, Map<Integer, CustomExchange>>> values = customExchangeDay.getValues();
            Map<Integer, Map<Integer, CustomExchange>> minuteMap = values.get(calendar.get(Calendar.HOUR_OF_DAY));
            Map<Integer, CustomExchange> secondMap = new LinkedHashMap<>();

            if (minuteMap == null) {
                secondMap.put(calendar.get(Calendar.SECOND), customExchange);
                minuteMap = new LinkedHashMap<>();
                minuteMap.put(calendar.get(Calendar.MINUTE), secondMap);
                values.put(calendar.get(Calendar.HOUR_OF_DAY), minuteMap);
                customExchangeDay.setValues(values);
            } else {
                secondMap = minuteMap.get(calendar.get(Calendar.MINUTE));
                if (secondMap == null) {
                    secondMap = new LinkedHashMap<>();


                }
                secondMap.put(calendar.get(Calendar.SECOND), customExchange);
                minuteMap.put(calendar.get(Calendar.MINUTE), secondMap);
            }
        }
        mongoTemplate.save(customExchangeDay);
    }


}
