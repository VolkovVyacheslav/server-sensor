package com.volkov.server.sensors.service.impl;

import com.volkov.server.sensors.dto.CreateMeasurementsDto;
import com.volkov.server.sensors.dto.MeasurementsDto;
import com.volkov.server.sensors.dto.SensorDto;
import com.volkov.server.sensors.mapper.MeasurementsMapper;
import com.volkov.server.sensors.repository.MeasurementsRepository;
import com.volkov.server.sensors.service.MeasurementsService;
import com.volkov.server.sensors.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MeasurementsServiceImpl implements MeasurementsService {

    private static final Logger log = LoggerFactory.getLogger(MeasurementsServiceImpl.class);

    private static final String LOG_APPLICATION_LEVEL = "Measurements service Level: ";

    private final MeasurementsMapper measurementsMapper;

    private final MeasurementsRepository measurementsRepository;

    private final SensorService sensorService;

    @Override
    public MeasurementsDto measurement(CreateMeasurementsDto measurements) {
        measurements.setTimeMeasurements(OffsetDateTime.now());
        log.info(LOG_APPLICATION_LEVEL + "Now registered measurements for sensor with id:"
                + measurements.getSensor().getId() + " at:"
                + measurements.getTimeMeasurements() + " time");
        measurements.setSensor(sensorService.isActive(measurements.getSensor()));
        return measurementsMapper
                .toDto(measurementsRepository.
                        save(measurementsMapper.
                                fromCreateDto(measurements)));
    }

    @Override
    public List<MeasurementsDto> getMeasurementsBySensor(String key) {
        List<MeasurementsDto> measurements = measurementsRepository
                .findTop20BySensorIdOrderByTimeMeasurementsDesc(UUID.fromString(key))
                .stream()
                .limit(20)
                .map(measurement ->measurementsMapper.toDto(measurement))
                .toList();
        log.info(LOG_APPLICATION_LEVEL + "Return last 20 measurements for sensor with id: " + key);
        return measurements;
    }

    @Override
    public List<MeasurementsDto> getMeasurementsAfterTimestamp(OffsetDateTime dateTime) {
        List<MeasurementsDto> measurements = measurementsRepository
                .findByTimeMeasurementsAfter(dateTime)
                .stream()
                .map(measurement ->measurementsMapper.toDto(measurement))
                .toList();
        log.info(LOG_APPLICATION_LEVEL + "Return measurements after " + dateTime + " timestamp");
        return measurements;
    }
}
