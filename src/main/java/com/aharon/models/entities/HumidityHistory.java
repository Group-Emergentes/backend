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
