package com.aharon.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "latest_sensor_record")
public class LatestSensorRegister {

    @Id
    @NotNull
    private String sensorId;

    private Date registerDate = new Date();

    @NotNull
    private Double value;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone;

    public LatestSensorRegister(HumidityHistory humidityHistory ) {
        this.sensorId = humidityHistory.getSensor().getSensorId();
        this.registerDate = humidityHistory.getRegisterDate();
        this.zone = humidityHistory.getZone();
        this.value = humidityHistory.getValue();

    }
    public LatestSensorRegister(TemperatureHistory temperatureHistory ) {
        this.sensorId = temperatureHistory.getSensor().getSensorId();
        this.registerDate = temperatureHistory.getRegisterDate();
        this.zone = temperatureHistory.getZone();
        this.value = temperatureHistory.getValue();
    }

}
