package com.proyecto.daw.dto.auth;

import com.proyecto.daw.persistence.entity.Usuario;
import lombok.Data;

import java.io.Serializable;

@Data
public class AuthResponse implements Serializable {

    private Usuario usuario;
    private String token;
}
