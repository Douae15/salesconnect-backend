package com.salesconnect.backend.service;

import com.salesconnect.backend.config.request.RegisterRequest;
import com.salesconnect.backend.config.response.JwtResponse;
import com.salesconnect.backend.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    public JwtResponse register(RegisterRequest request);
}
