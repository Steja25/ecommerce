package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.SneakyThrows;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	JwtFilter jwtFilter;
	@Autowired
	CustomUserDetailsService service;
//	@Bean
//	public DaoAuthenticationProvider authprovider() {
//		DaoAuthenticationProvider auth=new DaoAuthenticationProvider();
//		auth.setPasswordEncoder(encoder());
//		auth.setUserDetailsService(service);
//		return auth;
//		
//	}
//	
//	@Bean 
//	@SneakyThrows
//	public AuthenticationManager authManager(AuthenticationConfiguration config) {
//		
//		return config.getAuthenticationManager();
//	}
	@Bean
	public SecurityFilterChain filter(HttpSecurity http)  throws Exception{
		http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth 
            .requestMatchers("/api/auth/register", "/api/auth/login","/api/auth/users/**").permitAll() // public endpoints
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
            .anyRequest().authenticated()
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    // Add JWT filter before UsernamePasswordAuthenticationFilter
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
		
	}
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
