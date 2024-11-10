package com.aharon.sprinklers.dto;
import com.aharon.sprinklers.model.Sprinkler;
import lombok.Data;

import java.util.Date;

@Data
public class SprinklerResponse {

    private String sprinklerId;
    private Boolean active;
    private Date lastActivation;
    private Long zoneId;

    public SprinklerResponse(Sprinkler sprinkler) {

        this.sprinklerId = sprinkler.getSprinklerId();
        this.active = sprinkler.getActive();
        this.lastActivation = sprinkler.getLastActivation();
        this.zoneId = sprinkler.getZone().getId();
    }
}
