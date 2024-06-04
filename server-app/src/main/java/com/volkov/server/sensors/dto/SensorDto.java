package com.volkov.server.sensors.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SensorDto {

    private UUID id;

    private String name;

    private Boolean active;

    private List<MeasurementsDto> meassuriesList;
}
