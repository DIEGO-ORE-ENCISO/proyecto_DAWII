package com.proyecto.daw.dto;

import lombok.Data;

@Data
public class CarritoDTO {
    private Long idCarrito;
    private Integer cantidad;
    private Double totalCarrito;
    private Long idProducto;
    private Long idUser;
}
