package com.aharon.sprinklers.dto;

import lombok.Data;

@Data
public class ActiveRequest {
    private Long zoneId;
    private Boolean isManual;
}
