package com.aharon.models.entities;

import com.aharon.sensors.dto.CreateSensor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name= "sensors")
@Builder
public class Sensor {

    @Id
    @NotNull
    @Column(unique = true)
    private String sensorId;

    @NotNull
    private String type;

    @NotNull
    private Date lastConnection = new Date();

    @ManyToOne
    @JoinColumn(name = "zone_id", nullable = false)
    private Zone zone;

    public Sensor(CreateSensor createSensor) {
        this.sensorId = createSensor.getSensorId();
        this.type = createSensor.getType();

    }

}
