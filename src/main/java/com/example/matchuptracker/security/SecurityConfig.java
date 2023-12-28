package com.example.matchuptracker.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, @Value("${CORS_ALLOWED_ORIGIN:http://localhost:3000}") String corsAllowedOrigin) throws Exception {

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin(corsAllowedOrigin);
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");

        // Configure a UrlBasedCorsConfigurationSource and set the path pattern
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        // Add the CorsFilter to the HttpSecurity configuration
        http.cors().configurationSource(source);
        http.csrf().disable();

        http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/matchups/**").permitAll();
                    auth.requestMatchers("/secure").authenticated();
                })
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }
}
