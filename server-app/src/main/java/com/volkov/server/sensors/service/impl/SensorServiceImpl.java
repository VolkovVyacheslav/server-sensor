package com.volkov.server.sensors.service.impl;

import com.volkov.server.sensors.controller.payload.RegistrationSensor;
import com.volkov.server.sensors.dto.CreateSensorDto;
import com.volkov.server.sensors.dto.SensorDto;
import com.volkov.server.sensors.entity.Sensor;
import com.volkov.server.sensors.mapper.SensorMapper;
import com.volkov.server.sensors.repository.SensorRepository;
import com.volkov.server.sensors.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {

    private static final Logger log = LoggerFactory.getLogger(SensorServiceImpl.class);

    private static final String LOG_APPLICATION_LEVEL = "Sensor service Level: ";

    private final SensorRepository sensorRepository;

    private final SensorMapper sensorMapper;

    @Override
    public CreateSensorDto registerSensor(RegistrationSensor sensor) {
        log.info(LOG_APPLICATION_LEVEL + "Server is registration new sensor with name:" + sensor.name());
        return sensorMapper
                .toCreateDto(sensorRepository
                            .save(sensorMapper
                                    .fromCreateDto(SensorDto
                                            .builder()
                                            .name(sensor.name())
                                            .active(true)
                                            .build())));
    }

    @Override
    public SensorDto isActive(SensorDto sensor) {
        Sensor activeSensor = sensorRepository
                .findById(sensor.getId())
                .orElseThrow(NoSuchElementException::new);
        activeSensor.setActive(true);
        log.info(LOG_APPLICATION_LEVEL + "Now  sensor with id:"
                + activeSensor.getId() + " set is active");
        return sensorMapper
                .toDto(sensorRepository
                        .save(activeSensor));
    }

    @Override
    public List<CreateSensorDto> allActive() {
        log.info(LOG_APPLICATION_LEVEL + "Now return list of all active sensors");
        return sensorRepository
                .findAllByIsActive()
                .stream()
                .map(sensor -> sensorMapper.toCreateDto(sensor))
                .toList();
    }

    @Override
    public void isOffline(List<UUID> disableSensorsId) {
        log.info(LOG_APPLICATION_LEVEL + "Now disable " + disableSensorsId.size() + " sensors started!");
        disableSensorsId.forEach(uuid -> disable(sensorRepository.findById(uuid).orElseThrow()));
    }

    private Sensor disable(Sensor sensor){
        sensor.setActive(false);
        log.info(LOG_APPLICATION_LEVEL + "Now sensor with id: " + sensor.getId() + " is disable!");
        return sensorRepository.save(sensor);
    }

}
