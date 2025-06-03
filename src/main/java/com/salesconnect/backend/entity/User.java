package com.salesconnect.backend.entity;


import com.salesconnect.backend.entity.abstractEntity.AbstractEntity;
import com.salesconnect.backend.entity.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user")
public class User extends AbstractEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String password;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;

    @OneToMany(mappedBy = "user")
    private List<Activity> activities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Associer un rôle à l'utilisateur
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;  // Utiliser l'email comme identifiant
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Si l'on ne gère pas l'expiration des comptes
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Si l'on ne gère pas le verrouillage des comptes
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Si l'on ne gère pas l'expiration des credentials
    }

    @Override
    public boolean isEnabled() {
        return true; // Si l'on ne gère pas l'activation des comptes
    }

}

