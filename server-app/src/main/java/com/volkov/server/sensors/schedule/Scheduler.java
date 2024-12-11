package com.volkov.server.sensors.schedule;


import com.volkov.server.sensors.service.MeasurementsService;
import com.volkov.server.sensors.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class Scheduler {

    private static final Logger log = LoggerFactory.getLogger(Scheduler.class);

    private static final String LOG_APPLICATION_LEVEL = "Sensor scheduler Level: ";

    private final SensorService sensorService;

    private final MeasurementsService measurementsService;

    @Scheduled(cron = "${server.verify.processing.cron}")
    public void verifySensors()  {
       log.info(LOG_APPLICATION_LEVEL + "New job for verify active sensor started!");
        List<UUID> activeSensorsID = sensorService.allActive()
                .stream()
                .map(createSensorDto -> createSensorDto.getId())
                .collect(Collectors.toList());
        List<UUID> activeMeasurementsSensorsId = measurementsService
                .getMeasurementsAfterTimestamp(OffsetDateTime.now().minusMinutes(1L))
                .stream().map(measurementsDto -> measurementsDto.getSensor().getId())
                .collect(Collectors.toList());
        sensorService.isOffline(activeSensorsID.stream()
                .filter(activeSensorId-> !activeMeasurementsSensorsId.contains(activeSensorId))
                .collect(Collectors.toList()));
       log.info(LOG_APPLICATION_LEVEL + "New job for verify active sensor complete!");
    }
}
