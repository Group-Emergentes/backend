package com.aharon.clients.service.impl;

import com.aharon.clients.repository.ClientRepository;
import com.aharon.clients.service.ClientService;
import com.aharon.models.entities.Client;
import org.springframework.stereotype.Service;

@Service
public class ClientResponseImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientResponseImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }
}
