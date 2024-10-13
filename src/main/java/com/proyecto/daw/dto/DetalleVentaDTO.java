package com.proyecto.daw.dto;

import lombok.Data;

@Data
public class DetalleVentaDTO {
    private Long id;
    private Integer cantidad;
    private Double total;
    private Long idProducto;
}
