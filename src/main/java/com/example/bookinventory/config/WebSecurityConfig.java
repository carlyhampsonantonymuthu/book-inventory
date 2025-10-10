package com.example.bookinventory.config;

/*

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

*/
/**
 * Basic Spring Security configuration.
 *
 * ✅ Disables authentication for testing (all endpoints are public)
 * ✅ Enables basic form login if needed
 * ✅ Can be customized later for JWT or role-based security
 */

public class WebSecurityConfig {
/*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for simplicity during development
                .csrf(csrf -> csrf.disable())

                // Permit all endpoints (use .authenticated() later for secure routes)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )

                // Optionally enable default login form
                .formLogin(login -> login.permitAll())

                // Optionally enable HTTP Basic Auth (for quick testing with Postman)
                .httpBasic(httpBasic -> {});

        return http.build();
    }*/
}
