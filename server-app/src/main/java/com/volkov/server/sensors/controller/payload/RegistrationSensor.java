package com.volkov.server.sensors.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegistrationSensor(

        @NotNull(message = "{sensor.create.errors.name_is_null}")
        @Size(min=3, max=30, message = "{sensor.create.errors.title_size_is_invalid}")
        String name) {
}
