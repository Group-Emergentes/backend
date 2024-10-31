package com.aharon.models.entities;

import com.aharon.sensors.dto.CreateSensor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name= "sensor")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String sensorId;

    @NotNull
    private Float value;

    @NotNull
    private String type;

    @NotNull
    private Long zoneId;

    @NotNull
    private LocalDateTime lastConnection;


    // Constructor con parámetros
    public Sensor(CreateSensor createSensor) {
        this.sensorId = createSensor.getSensorId();
        this.value = createSensor.getValue();
        this.type = createSensor.getType();
        this.zoneId = createSensor.getZoneId();
        this.lastConnection = createSensor.getLastConnection();
    }

}
