package com.salesconnect.backend.service.impl;

import com.salesconnect.backend.dto.UserDTO;
import com.salesconnect.backend.entity.Company;
import com.salesconnect.backend.entity.User;
import com.salesconnect.backend.entity.enums.Role;
import com.salesconnect.backend.repository.UserRepository;
import com.salesconnect.backend.service.UserService;
import com.salesconnect.backend.transformer.CompanyTransformer;
import com.salesconnect.backend.transformer.UserTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserTransformer userTransformer = new UserTransformer();


    @Override
    public UserDTO createUser(UserDTO userDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String adminEmail;
        if (principal instanceof UserDetails) {
            adminEmail = ((UserDetails) principal).getUsername();
        } else {
            adminEmail = principal.toString();
        }

        User admin = userRepository.findByEmail(adminEmail);

        if (admin == null || admin.getRole() != Role.ADMIN_COMPANY) {
            throw new RuntimeException("Unauthorized");
        }
/*
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already in use!");
        }
*/
        User newUser = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .phone(userDTO.getPhone())
                .role(Role.USER)
                .company(admin.getCompany())
                .build();

        newUser = userRepository.save(newUser);

        return userTransformer.toDTO(newUser);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userTransformer.toDTOList(users); // Transformer les entités en DTO et renvoyer
    }

    @Override
    public UserDTO getUserById(Long id) {
        log.info("Recherche de l'utilisateur avec ID: " + id);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            log.info("Utilisateur trouvé: " + user.get().getEmail());
        } else {
            log.warn("Utilisateur non trouvé avec ID: " + id);
        }
        return user.map(userTransformer::toDTO).orElse(null);
    }


    @Override
    public List<UserDTO> getUsersForCompany() {
        // Récupérer l'admin actuellement connecté à partir du contexte de sécurité
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String adminEmail = (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername() : principal.toString();

        // Trouver l'admin par email
        User admin = userRepository.findByEmail(adminEmail);

        if (admin == null || admin.getRole() != Role.ADMIN_COMPANY) {
            throw new RuntimeException("Unauthorized");
        }

        // Récupérer les utilisateurs de l'entreprise de l'admin
        List<User> users = userRepository.findByCompany(admin.getCompany());

        // Transformer les utilisateurs en DTO et retourner
        return users.stream()
                .map(user -> userTransformer.toDTO(user))
                .collect(Collectors.toList());
    }



    /*@Override
    public UserDTO createUser(UserDTO userDTO) {
        return null;
    }*/

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Si le mot de passe est fourni, l'encoder avant de le sauvegarder
        String password = userDTO.getPassword() != null ? passwordEncoder.encode(userDTO.getPassword()) : existingUser.getPassword();

        // Mettre à jour les autres informations
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPhone(userDTO.getPhone());
        existingUser.setPassword(password);

        // Sauvegarder les modifications
        userRepository.save(existingUser);

        // Transformer l'utilisateur modifié en DTO et le retourner
        return userTransformer.toDTO(existingUser);
    }


    @Override
    public boolean deleteUser(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(existingUser);
        return true; // Retourner true pour confirmer la suppression
    }

}
