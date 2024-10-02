package com.proyecto.daw.dto;

import com.proyecto.daw.persistence.entity.Usuario;
import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterUsuario implements Serializable {

    private Usuario usuario;
    private String mensaje;

}
