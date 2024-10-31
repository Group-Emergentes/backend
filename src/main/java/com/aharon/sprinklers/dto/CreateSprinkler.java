package com.aharon.sprinklers.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CreateSprinkler {
    @NotNull
    private String sprinklerId;

    @NotNull
    private String name;

    @NotNull
    private Boolean state;

    @NotNull
    private LocalDateTime lastConnection;

}
