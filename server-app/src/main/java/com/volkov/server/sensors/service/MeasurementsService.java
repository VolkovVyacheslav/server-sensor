package com.volkov.server.sensors.service;

import com.volkov.server.sensors.dto.CreateMeasurementsDto;
import com.volkov.server.sensors.dto.MeasurementsDto;
import com.volkov.server.sensors.dto.SensorDto;

import java.time.OffsetDateTime;
import java.util.List;

public interface MeasurementsService {

    MeasurementsDto measurement(CreateMeasurementsDto measurements);

    List<MeasurementsDto> getMeasurementsBySensor(String key);

    List<MeasurementsDto> getMeasurementsAfterTimestamp(OffsetDateTime dateTime);

}
