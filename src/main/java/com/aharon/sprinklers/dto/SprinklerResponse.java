package com.aharon.sprinklers.dto;
import com.aharon.models.entities.Sprinkler;
import lombok.Data;

import java.util.Date;

@Data
public class SprinklerResponse {

    private String sprinklerId;
    private Boolean state;
    private Date lastConnection;
    private Long zoneId;

    public SprinklerResponse(Sprinkler sprinkler) {

        this.sprinklerId = sprinkler.getSprinklerId();
        this.state = sprinkler.getState();
        this.lastConnection = sprinkler.getLastConnection();
        this.zoneId = sprinkler.getZone().getId();
    }
}
