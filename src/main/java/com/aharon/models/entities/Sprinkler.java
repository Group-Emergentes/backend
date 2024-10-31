package com.aharon.models.entities;

import com.aharon.sprinklers.dto.CreateSprinkler;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private Boolean state = false;

    @NotNull
    private Date lastConnection = new Date();

    @ManyToOne
    @JoinColumn(name = "zone_id", nullable = false)
    private Zone zone;

    public Sprinkler(CreateSprinkler createSprinkler) {
        this.sprinklerId = createSprinkler.getSprinklerId();

    }
}
