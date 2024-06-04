package com.volkov.server.sensors.mapper.impl;


import com.volkov.server.sensors.dto.CreateSensorDto;
import com.volkov.server.sensors.dto.SensorDto;
import com.volkov.server.sensors.entity.Sensor;
import com.volkov.server.sensors.mapper.MeasurementsMapper;
import com.volkov.server.sensors.mapper.SensorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SensorMapperImpl implements SensorMapper {

    private final MeasurementsMapper measurementsMapper;

    @Override
    public Sensor fromCreateDto(SensorDto dto) {
        return Sensor.builder()
                .name(dto.getName())
                .active(dto.getActive())
                .build();
    }

    @Override
    public SensorDto toDto(Sensor entity) {
        return SensorDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .active(entity.getActive())
                .meassuriesList(entity.getMeassuriesList().stream()
                        .map(measurements -> measurementsMapper.toDto(measurements))
                        .toList())
                .build();
    }

    @Override
    public CreateSensorDto toCreateDto(Sensor entity) {
        return CreateSensorDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .active(entity.getActive())
                .build();
    }
}
