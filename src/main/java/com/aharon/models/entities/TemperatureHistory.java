package com.aharon.models.entities;

import com.aharon.sensors.dto.TemperatureRegister;
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
@Builder
@Table(name= "temperature_history")
public class TemperatureHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date registerDate = new Date();

    @NotNull
    private Double value;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone;

}
