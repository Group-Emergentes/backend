package com.aharon.auth.service;

import com.aharon.auth.dto.AuthResponse;
import com.aharon.clients.dto.ClientRequest;

public interface AccountService {

    AuthResponse login(String username, String password);
    AuthResponse register(ClientRequest clientRequest);

}
