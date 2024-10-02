package com.proyecto.daw.dto.auth;

import com.proyecto.daw.persistence.entity.Usuario;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoginRequest implements Serializable {

    private String username;
    private String password;
}
