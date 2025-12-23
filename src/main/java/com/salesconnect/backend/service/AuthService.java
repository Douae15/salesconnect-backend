package com.salesconnect.backend.service;

import com.salesconnect.backend.config.request.LoginRequest;
import com.salesconnect.backend.config.request.RegisterRequest;
import com.salesconnect.backend.config.response.JwtResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    public JwtResponse login(LoginRequest authenticationRequest);
    public JwtResponse register(RegisterRequest request);
}
