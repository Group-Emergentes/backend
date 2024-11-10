package com.aharon.sprinklers.model;

import com.aharon.zones.model.entities.Zone;
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
@Table(name= "sprinklers")
public class Sprinkler {

    @Id
    @NotNull
    private String sprinklerId;

    @NotNull
    private Boolean active;

    private Date lastActivation;

    private Boolean isManualActivated;

    private Boolean isAutomaticActivated;

    @ManyToOne
    @JoinColumn(name = "zone_id", nullable = false)
    private Zone zone;

    public Sprinkler(String sprinklerId) {
        this.sprinklerId = sprinklerId;
        this.isManualActivated = false;
        this.isAutomaticActivated= false;
        this.lastActivation = new Date();
        this.active = false;

    }
}
