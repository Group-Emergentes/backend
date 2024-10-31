package com.aharon.sprinklers.dto;
import com.aharon.models.entities.Sprinkler;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SprinklerResponse {
    private Long id;
    private String sprinklerId;
    private String name;
    private Boolean state;
    private LocalDateTime lastConnection;

    public SprinklerResponse(Sprinkler sprinkler) {
        this.id = sprinkler.getId();
        this.sprinklerId = sprinkler.getSprinklerId();
        this.name = sprinkler.getName();
        this.state = sprinkler.getState();
        this.lastConnection = sprinkler.getLastConnection();
    }
}
