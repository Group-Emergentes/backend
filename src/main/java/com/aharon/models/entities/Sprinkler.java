package com.aharon.models.entities;

import com.aharon.sprinklers.dto.CreateSprinkler;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name= "sprinkler")
public class Sprinkler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String sprinklerId;

    @NotNull
    private String name;

    @NotNull
    private Boolean state;

    @NotNull
    private LocalDateTime lastConnection;

    public Sprinkler(CreateSprinkler createSprinkler) {
        this.sprinklerId = createSprinkler.getSprinklerId();
        this.name = createSprinkler.getName();
        this.state = createSprinkler.getState();
        this.lastConnection = createSprinkler.getLastConnection();
    }
}
