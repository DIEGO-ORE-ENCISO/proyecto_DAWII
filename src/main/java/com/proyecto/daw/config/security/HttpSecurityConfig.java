package com.proyecto.daw.config.security;

import com.proyecto.daw.config.security.filter.JwtAuthenticationFilter;
import com.proyecto.daw.persistence.util.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] roles = { Rol.ADMINISTRADOR.name(), Rol.ASISTENTE.name(), Rol.USUARIO.name()};

        return http
                .cors(cors -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
                    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
                    corsConfiguration.setAllowCredentials(true);
                })
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authRequest ->{
                    authRequest.requestMatchers("/auth/**").permitAll();
                    authRequest.requestMatchers("/user/**").hasRole(Rol.ADMINISTRADOR.name());
                    authRequest.anyRequest().authenticated();
                })
                .exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler()))
                .build();

    }


    @Bean
    public AccessDeniedHandler accessDeniedHandler() { return new ManejadorAccesoDenegadoPersonalizado();}
}