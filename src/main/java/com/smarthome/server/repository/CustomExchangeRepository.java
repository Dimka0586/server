package com.smarthome.server.repository;

import com.smarthome.server.model.types.CustomExchange;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomExchangeRepository extends MongoRepository<CustomExchange, String> {
}
