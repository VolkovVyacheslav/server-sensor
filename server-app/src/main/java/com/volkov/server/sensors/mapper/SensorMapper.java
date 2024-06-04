package com.volkov.server.sensors.mapper;


import com.volkov.server.sensors.dto.CreateSensorDto;
import com.volkov.server.sensors.dto.SensorDto;
import com.volkov.server.sensors.entity.Sensor;

public interface SensorMapper {

    Sensor fromCreateDto(SensorDto dto);

    SensorDto toDto(Sensor entity);

    CreateSensorDto toCreateDto(Sensor entity);
}
