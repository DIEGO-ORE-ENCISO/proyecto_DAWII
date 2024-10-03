package com.proyecto.daw.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoriaDTO {
    private Long id;
    private String nombreCategoriaProd;
    private String estadoCategoria;
}
