package com.smarthome.server.repository;

import com.smarthome.server.model.Device;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends MongoRepository<Device, String> {

    Device findDeviceByName(String name);
    Device findDeviceBy_id(String _id);

}
