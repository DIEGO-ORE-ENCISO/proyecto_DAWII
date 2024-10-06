package com.proyecto.daw.persistence.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tb_producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_producto", nullable = false)
    private String nombreProducto;

    @Column(name = "descripcion", nullable = true)
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "stock_prod", nullable = false)
    private Integer stock;

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "estado_producto", nullable = false)
    private String estadoProducto;

    // Relación con la tabla de categoría de producto
    @ManyToOne
    @JoinColumn(name = "categoria_prod_id", nullable = false)
    private CategoriaProd categoriaProd;
}
