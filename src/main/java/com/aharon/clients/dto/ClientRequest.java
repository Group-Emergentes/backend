package com.aharon.clients.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientRequest {

    @NotNull
    private String name;

    @NotNull
    private String cellphone;

    @NotNull
    private String email;

    @NotNull
    private String password;

}