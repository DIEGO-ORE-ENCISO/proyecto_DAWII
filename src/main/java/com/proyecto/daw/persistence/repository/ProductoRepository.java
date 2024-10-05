package com.proyecto.daw.persistence.repository;

import com.proyecto.daw.persistence.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
