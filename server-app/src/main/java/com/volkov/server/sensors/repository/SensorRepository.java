package com.volkov.server.sensors.repository;

import com.volkov.server.sensors.entity.Sensor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SensorRepository extends CrudRepository<Sensor, UUID> {

    @Query(value = "SELECT t.* FROM server_sensor.t_sensors t WHERE t.active=true", nativeQuery = true)
    List<Sensor> findAllByIsActive();
}
