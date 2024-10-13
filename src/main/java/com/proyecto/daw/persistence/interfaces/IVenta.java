package com.proyecto.daw.persistence.interfaces;

import com.proyecto.daw.dto.VentaDTO;
import com.proyecto.daw.persistence.entity.Venta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IVenta {
    // Obtener un historial de todas las ventas con paginación
    Page<VentaDTO> getHistorialVentas(Pageable pageable);

    // Obtener un historial de ventas por usuario con paginación
    Page<Venta> getHistorialVentasPorUsuario(Long usuarioId, Pageable pageable);

    // Eliminar un historial de ventas por ID
    void eliminarHistorialVentas(Long id);
}
