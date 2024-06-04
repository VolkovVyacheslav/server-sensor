package com.volkov.server.sensors.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementsDto {

    private UUID id;

    private Double temp;

    private Boolean rain;

    private OffsetDateTime timeMeasurements;

    private SensorDto sensor;
}
