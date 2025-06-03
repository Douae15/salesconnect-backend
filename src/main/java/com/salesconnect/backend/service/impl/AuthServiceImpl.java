package com.salesconnect.backend.service.impl;

import com.salesconnect.backend.config.jwt.JwtTokenProvider;
import com.salesconnect.backend.config.request.RegisterRequest;
import com.salesconnect.backend.config.response.JwtResponse;
import com.salesconnect.backend.dto.UserDTO;
import com.salesconnect.backend.entity.Company;
import com.salesconnect.backend.entity.User;
import com.salesconnect.backend.entity.enums.Role;
import com.salesconnect.backend.repository.CompanyRepository;
import com.salesconnect.backend.repository.UserRepository;
import com.salesconnect.backend.service.AuthService;
import com.salesconnect.backend.transformer.UserTransformer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    public CompanyRepository companyRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public UserTransformer userTransformer;
    @Autowired
    public PasswordEncoder passwordEncoder;
    @Autowired
    public JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
public JwtResponse register(RegisterRequest request) {
    // Créer l'entreprise
    Company company = new Company();
    company.setName(request.getCompanyName());
    company.setAddress(request.getCompanyAddress());
    company.setPhone(request.getCompanyPhone());
    company.setEmail(request.getCompanyEmail());
    company.setIndustry(request.getCompanyIndustry());
    company.setCountry(request.getCompanyCountry());
    Company savedCompany = companyRepository.save(company);

    // Créer l'utilisateur Admin d'Entreprise
    User user = new User();
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setEmail(request.getEmail());
    user.setPhone(request.getPhone());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRole(Role.ADMIN_COMPANY);
    user.setCompany(savedCompany);

    User savedUser = userRepository.save(user);

    // Authentifier l'utilisateur avec AuthenticationManager
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    // Générer un token JWT
    String token = jwtTokenProvider.createToken(authentication);

    // Retourner JwtResponse avec le token et email (tu peux ajouter plus d’infos si tu veux)
    return new JwtResponse(token, "Bearer", savedUser.getUserId(), savedUser.getFirstName() + " " + savedUser.getLastName(), savedUser.getEmail(), List.of(user.getRole().name()));
}


}
