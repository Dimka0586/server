package com.smarthome.server.repository;

import com.smarthome.server.model.DeviceValueDay;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface DeviceValueDayRepository extends MongoRepository<DeviceValueDay, String> {

    DeviceValueDay findDeviceValueDayByDate(Date date);
}
