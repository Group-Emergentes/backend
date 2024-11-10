package com.aharon.models.entities;

import com.aharon.auth.model.Account;
import com.aharon.clients.dto.ClientRequest;
import com.aharon.zones.model.entities.Zone;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String cellPhone;

    @NotNull
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Zone> zoneList = null;

    public Client(ClientRequest clientRequest, Account account) {
        this.name = clientRequest.getName();
        this.cellPhone = clientRequest.getCellPhone();
        this.email = clientRequest.getEmail();
        this.account = account;
    }
}
