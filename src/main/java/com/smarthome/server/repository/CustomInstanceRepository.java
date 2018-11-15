package com.smarthome.server.repository;

import com.smarthome.server.model.types.CustomInstance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomInstanceRepository extends MongoRepository<CustomInstance, String> {
}
