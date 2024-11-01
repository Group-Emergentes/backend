package com.aharon.models.entities;

import com.aharon.sensors.dto.TemperatureRegister;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name= "temperature_history")
public class TemperatureHistory {

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


    public TemperatureHistory(TemperatureRegister temperatureRegister){
        this.sensorId = temperatureRegister.getSensorId();
        this.value = temperatureRegister.getValue();
        this.zoneId = temperatureRegister.getZoneId();
    }


}
