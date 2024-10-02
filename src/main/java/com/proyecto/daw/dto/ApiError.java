package com.proyecto.daw.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ApiError implements Serializable {

    private String backendMensaje;

    private String uri;

    private String metodo;

    private LocalDateTime fecha;

    private String mensaje;

}
