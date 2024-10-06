package com.proyecto.daw.dto;


import lombok.Data;

@Data
public class ProductoDTO {
    private Long id;
    private String nombreProducto;
    private String descripcion;
    private Double precio;
    private String estadoProducto;
    private Integer stock;
    private String imagen;
    private Long categoriaProdId; // Para enlazar con la categoría
}
