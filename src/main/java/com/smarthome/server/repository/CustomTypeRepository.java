package com.smarthome.server.repository;

import com.smarthome.server.model.types.CustomType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomTypeRepository extends MongoRepository<CustomType, String> {
}
