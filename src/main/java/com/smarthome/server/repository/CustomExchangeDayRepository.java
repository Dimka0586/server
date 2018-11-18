package com.smarthome.server.repository;

import com.smarthome.server.model.types.CustomExchangeDay;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomExchangeDayRepository extends MongoRepository<CustomExchangeDay, String> {
}
