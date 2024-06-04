package com.volkov.server.sensors.mapper;

import com.volkov.server.sensors.dto.CreateMeasurementsDto;
import com.volkov.server.sensors.dto.MeasurementsDto;
import com.volkov.server.sensors.entity.Measurements;

public interface MeasurementsMapper {

    Measurements fromCreateDto(CreateMeasurementsDto dto);

    MeasurementsDto toDto(Measurements entity);
}
