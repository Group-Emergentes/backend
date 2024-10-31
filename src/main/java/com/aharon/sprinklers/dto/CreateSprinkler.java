package com.aharon.sprinklers.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSprinkler {

    @NotNull
    private String sprinklerId;
    @NotNull
    private Long zoneId;

}
