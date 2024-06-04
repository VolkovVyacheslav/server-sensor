package com.volkov.server.sensors.controller.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public record MeasurementSensor(


        @NotNull(message = "{sensor.measurement.errors.temp_is_null}")
        @Min(value = -100, message = "{sensor.measurement.errors.temp_is_invalid}")
        @Max(value = 100, message = "{sensor.measurement.errors.temp_is_invalid}")
        Double value,

        @NotNull(message = "{sensor.measurement.errors.rain_is_null}")
        Boolean raining) {
}
