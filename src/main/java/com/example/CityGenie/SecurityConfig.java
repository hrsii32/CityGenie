package com.example.CityGenie;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration

public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for testing
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/hello").permitAll() // Public endpoint
                .anyRequest().authenticated() // Protect all other endpoints
                )
                .httpBasic(basic -> {
                }); // Use lambda syntax, avoids deprecation

        return http.build();
    }

}
