package com.smarthome.server.repository;

import com.smarthome.server.model.DiscreteActuator;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscreteActuatorRepository extends MongoRepository<DiscreteActuator, String> {
}
