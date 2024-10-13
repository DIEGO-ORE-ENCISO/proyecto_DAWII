package com.proyecto.daw.persistence.repository;

import com.proyecto.daw.persistence.entity.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleVentaRepo extends JpaRepository<DetalleVenta, Long> {
}
