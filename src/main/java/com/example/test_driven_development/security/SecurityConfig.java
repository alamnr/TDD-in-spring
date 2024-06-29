package com.example.test_driven_development.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {
    
    @Bean 
    public  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests(requests ->  requests
                .requestMatchers(HttpMethod.GET,"/dashboard").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/tasks/**").authenticated()
                .requestMatchers("/api/users/**").permitAll()
                //.requestMatchers("/**").authenticated()
                .anyRequest().authenticated()
            )
        //.csrf(AbstractHttpConfigurer::disable)
        .httpBasic(Customizer.withDefaults())
        .build();
    }

    
}
