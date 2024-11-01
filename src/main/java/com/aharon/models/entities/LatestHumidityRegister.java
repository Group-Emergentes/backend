package com.aharon.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "latest_humidity_record")
public class LatestHumidityRegister {
    @Id
    private String sensorId;

    @NotNull
    private Date registerDate;

    @NotNull
    private Double value;

    @NotNull
    private Long zoneId;

    public LatestHumidityRegister(HumidityHistory humidityHistory) {
        this.sensorId = humidityHistory.getSensorId();
        this.registerDate = humidityHistory.getRegisterDate();
        this.value = humidityHistory.getValue();
        this.zoneId = humidityHistory.getZoneId();
    }
}
