package com.volkov.server.sensors.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder(toBuilder = true)
@Table(schema= "server_sensor", name = "t_sensors", uniqueConstraints =
        {
                @UniqueConstraint(columnNames = "name")
        })
@NoArgsConstructor
@AllArgsConstructor
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL)
    private List<Measurements> meassuriesList;
}
