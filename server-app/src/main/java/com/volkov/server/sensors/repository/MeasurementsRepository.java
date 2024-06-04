package com.volkov.server.sensors.repository;

import com.volkov.server.sensors.entity.Measurements;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MeasurementsRepository extends CrudRepository<Measurements, UUID> {

    List<Measurements> findTop20BySensorIdOrderByTimeMeasurementsDesc(UUID sensorId);

    List<Measurements> findByTimeMeasurementsAfter(OffsetDateTime offsetDateTime);
}
