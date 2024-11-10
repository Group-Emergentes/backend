package com.aharon.sprinklers.model;

import com.aharon.sprinklers.model.valueobjets.ActivationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sprinkler_activations_history")
public class SprinklerActivationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String sprinklerId;

    @NotNull
    private Date activationTime;

    @NotNull
    private Date deactivationTime;

    @Enumerated(EnumType.STRING)
    private ActivationType activationType;

    private Long zoneId;

    public SprinklerActivationHistory(Sprinkler sprinkler, ActivationType activationType) {
        this.sprinklerId = sprinkler.getSprinklerId();
        this.activationTime = sprinkler.getLastActivation();
        this.deactivationTime = new Date();
        this.activationType = activationType;
        this.zoneId = sprinkler.getZone().getId();
    }
}
