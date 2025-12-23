package com.salesconnect.backend.controller;

import com.salesconnect.backend.dto.UserDTO;
import com.salesconnect.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "The User API")
@Slf4j
public class UserController {

   @Autowired
    private UserService userService;

    /*@PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }*/

    @GetMapping(path = "/all")
    public ResponseEntity<List<UserDTO>> getUserList() {
        log.info("Invoke Get All Users end point");
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentification de l'utilisateur: " + authentication.getName());

        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("Utilisateur non authentifié");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDTO user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }



    // Récupérer tous les utilisateurs d'une entreprise spécifique (en fonction de l'email de l'admin)
    @GetMapping("/getByCompany")
    public ResponseEntity<List<UserDTO>> getUsersForCompany() {
        List<UserDTO> users = userService.getUsersForCompany();
        return ResponseEntity.ok(users);
    }

    // Mettre à jour un utilisateur
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
    }

    // Supprimer un utilisateur
   /* @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    } */
    
}
