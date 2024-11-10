package com.aharon.models.entities;

import com.aharon.sprinklers.dto.CreateSprinkler;
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
    private Boolean active = false;

    private Date lastActivation;

    @ManyToOne
    @JoinColumn(name = "zone_id", nullable = false)
    private Zone zone;

    public Sprinkler(CreateSprinkler createSprinkler) {
        this.sprinklerId = createSprinkler.getSprinklerId();

    }
}
