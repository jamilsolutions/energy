package com.energy.repository;

import com.energy.model.Meter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeterRepository extends MongoRepository<Meter, String> {

    Meter findByMeterNo(Long meterNo);

}
