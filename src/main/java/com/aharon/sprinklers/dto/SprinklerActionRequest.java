package com.aharon.sprinklers.dto;

import lombok.Data;

@Data
public class SprinklerActionRequest {
    private Long zoneId;
    private Boolean isManual;
    private Boolean active;
}
