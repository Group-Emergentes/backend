package com.aharon.clients.dto;

import com.aharon.models.entities.Client;
import lombok.Data;

@Data
public class ClientResponse {

    private Long id;
    private String name;
    private String cellPhone;
    private String email;

    public ClientResponse(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.cellPhone = client.getCellPhone();
        this.email = client.getEmail();
    }
}
