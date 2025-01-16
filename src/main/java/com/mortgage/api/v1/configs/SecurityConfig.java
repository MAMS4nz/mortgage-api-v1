package com.mortgage.api.v1.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                    .ignoringRequestMatchers("/api/**") //No need for CSRF protection for now.
            )
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/management/health",
                            "/management/info",
                            "/management/metrics"
                    ).hasAnyRole("ADMIN")
                    .requestMatchers("/management/**").authenticated()
                    .anyRequest().permitAll()
            )
            .httpBasic(Customizer.withDefaults()); // Just for Actuator
        return http.build();
    }

}

