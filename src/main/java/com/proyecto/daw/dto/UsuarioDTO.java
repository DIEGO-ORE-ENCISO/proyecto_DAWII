package com.proyecto.daw.dto;

import com.proyecto.daw.persistence.util.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UsuarioDTO implements Serializable {

    private Long id;
    private String nombre;
    private String apellido;
    private String username;
    private String email;
    private Rol rol;
}
