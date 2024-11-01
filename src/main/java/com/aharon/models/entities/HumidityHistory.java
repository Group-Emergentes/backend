package com.aharon.models.entities;

import com.aharon.sensors.dto.HumidityRegister;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "humidity_history")
public class HumidityHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String sensorId;

    @NotNull
    private Date registerDate = new Date();

    @NotNull
    private Double value;

    @NotNull
    private Long zoneId;

    public HumidityHistory(HumidityRegister humidityRegister){
        this.sensorId = humidityRegister.getSensorId();
        this.value = humidityRegister.getValue();
        this.zoneId = humidityRegister.getZoneId();
    }
}
