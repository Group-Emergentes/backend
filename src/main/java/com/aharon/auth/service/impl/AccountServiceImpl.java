package com.aharon.auth.service.impl;

import com.aharon.auth.dto.AuthResponse;
import com.aharon.auth.model.Account;
import com.aharon.auth.repository.AccountRepository;
import com.aharon.auth.service.AccountService;
import com.aharon.clients.dto.ClientRequest;
import com.aharon.clients.dto.ClientResponse;
import com.aharon.clients.repository.ClientRepository;
import com.aharon.models.entities.Client;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    public AccountServiceImpl(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public AuthResponse login(String username, String password) {
        Account account = accountRepository.findByUsername(username);

        if (account == null) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        if (!account.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password");
        }

        return AuthResponse.builder()
                .token("token here")
                .client(new ClientResponse(account.getClient()))
                .build();
    }

    @Override
    public AuthResponse register(ClientRequest clientRequest) {
        Account account = accountRepository.findByUsername(clientRequest.getEmail());
        if(account != null){
            throw new IllegalArgumentException("Account with email "+ clientRequest.getEmail()+" already exists");
        }
        account = new Account(clientRequest.getEmail(), clientRequest.getPassword());
        account = accountRepository.save(account);

        Client client = new Client(clientRequest, account);
        clientRepository.save(client);

        return AuthResponse.builder()
                .token("token here")
                .client(new ClientResponse(account.getClient()))
                .build();
    }
}
