package com.volkov.server.sensors.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Builder(toBuilder = true)
@Table(schema= "server_sensor", name = "t_measurements")
@NoArgsConstructor
@AllArgsConstructor
public class Measurements {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Min(value = -100)
    @Max(value = 100)
    @Column(name = "temp")
    private Double temp;

    @NotNull
    @Column(name = "rain")
    private Boolean rain;

    @NotNull
    @Column(name = "time_measurements")
    private OffsetDateTime timeMeasurements;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;
}
