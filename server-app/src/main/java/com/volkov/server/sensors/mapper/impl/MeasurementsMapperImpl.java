package com.volkov.server.sensors.mapper.impl;

import com.volkov.server.sensors.dto.CreateMeasurementsDto;
import com.volkov.server.sensors.dto.MeasurementsDto;
import com.volkov.server.sensors.dto.SensorDto;
import com.volkov.server.sensors.entity.Measurements;
import com.volkov.server.sensors.entity.Sensor;
import com.volkov.server.sensors.mapper.MeasurementsMapper;
import org.springframework.stereotype.Component;

@Component
public class MeasurementsMapperImpl implements MeasurementsMapper {
    @Override
    public Measurements fromCreateDto(CreateMeasurementsDto dto) {
        return Measurements.builder()
                .temp(dto.getTemp())
                .rain(dto.getRain())
                .timeMeasurements(dto.getTimeMeasurements())
                .sensor(Sensor.builder()
                        .id(dto.getSensor().getId())
                        .build())
                .build();
    }

    @Override
    public MeasurementsDto toDto(Measurements entity) {
        return MeasurementsDto.builder()
                .id(entity.getId())
                .temp(entity.getTemp())
                .rain(entity.getRain())
                .timeMeasurements(entity.getTimeMeasurements())
                .sensor(SensorDto.builder()
                        .id(entity.getSensor().getId())
                        .build())
                .build();
    }
}
