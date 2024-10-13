package com.proyecto.daw.persistence.repository;

import com.proyecto.daw.persistence.entity.Venta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepo extends JpaRepository<Venta, Long> {
    // Método para encontrar ventas por ID de usuario
    @Query("SELECT v FROM Venta v WHERE v.usuario.id = :usuarioId")
    Page<Venta> findByUsuarioId(Long usuarioId, Pageable pageable);
}
