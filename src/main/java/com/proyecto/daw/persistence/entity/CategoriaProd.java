package com.proyecto.daw.persistence.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tb_categoria_prod")
public class CategoriaProd  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_categoria_prod")
    private String nombreCategoriaProd;

    @Column(name = "estado_categoria")
    private String estadoCategoria;
}
