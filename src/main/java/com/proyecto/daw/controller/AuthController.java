package com.proyecto.daw.controller;

import com.proyecto.daw.dto.RegisterUsuario;
import com.proyecto.daw.dto.SaveUsuario;
import com.proyecto.daw.dto.auth.AuthResponse;
import com.proyecto.daw.dto.auth.LoginRequest;
import com.proyecto.daw.services.auth.JwtService;
import com.proyecto.daw.services.impl.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody  @Valid LoginRequest loginRequest) {
        AuthResponse authResponse = authService.login(loginRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUsuario> register(@RequestBody @Valid SaveUsuario saveUsuario) {
        RegisterUsuario registerUsuario =  authService.registerUsuario(saveUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUsuario);
    }


    @GetMapping("/validar-token")
    public ResponseEntity<AuthResponse> validarToken(@RequestHeader("Authorization") String authorizationHeader) {
        // Verificar que el encabezado Authorization contiene el prefijo "Bearer "
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Extraer el token JWT del encabezado
        String token = authorizationHeader.substring(7);

        AuthResponse authResponse = authService.validateToken(token);
        return ResponseEntity.ok(authResponse);
    }







}
