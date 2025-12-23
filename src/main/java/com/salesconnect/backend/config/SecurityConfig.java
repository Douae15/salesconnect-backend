package com.salesconnect.backend.config;

import com.salesconnect.backend.config.jwt.JwtAuthenticationEntryPoint;
import com.salesconnect.backend.config.jwt.JwtTokenFilter;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    private final JwtTokenFilter jwtAuthenticationFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SecurityConfig(JwtTokenFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors().and()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .authorizeHttpRequests(authorize -> authorize
                        // 🔓 Endpoints publics
                        .requestMatchers("/api/auth/**").permitAll() // Authentification
                        .requestMatchers("/public/**").permitAll() // Pages publiques

                        // 🔒 Accès réservé à l'Admin Global
                        .requestMatchers("/api/admin/**", "/api/companies/all", "/api/companies/get/{id}")
                        .hasAuthority("ADMIN_GLOBAL")

                        // 🔒 Admin d’entreprise : accès uniquement à sa propre entreprise
                        .requestMatchers(HttpMethod.GET, "/api/companies/{companyId}").hasAuthority("ADMIN_COMPANY")
                        .requestMatchers(HttpMethod.PUT, "/api/companies/{companyId}").hasAuthority("ADMIN_COMPANY")

                        // 🔒 Accès réservé à l'Admin d’entreprise
                        //.requestMatchers("/api/users/create").hasAuthority("ADMIN_COMPANY")
                        .requestMatchers("/api/contacts/**").hasAuthority("ADMIN_COMPANY")
                        .requestMatchers("/api/opportunities/**").hasAuthority("ADMIN_COMPANY")
                        .requestMatchers("/api/products/**").hasAuthority("ADMIN_COMPANY")

                        // Admin d’entreprise : gestion de son profil utilisateur
                        .requestMatchers(HttpMethod.PUT, "/api/users/update/{id}").hasAuthority("ADMIN_COMPANY")
                        .requestMatchers(HttpMethod.GET, "/api/users/get/{id}").hasAuthority("ADMIN_COMPANY")

                        // 🔐 Tout autre accès nécessite l'authentification
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("*") // Autoriser toutes les méthodes
                .allowedHeaders("*") // Autoriser tous les en-têtes
                .allowCredentials(true); // Autoriser les cookies
    }

}
