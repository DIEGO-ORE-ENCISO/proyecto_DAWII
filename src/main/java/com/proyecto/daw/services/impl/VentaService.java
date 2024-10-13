package com.proyecto.daw.services.impl;

import com.proyecto.daw.dto.VentaDTO;
import com.proyecto.daw.persistence.entity.Venta;
import com.proyecto.daw.persistence.interfaces.IVenta;
import com.proyecto.daw.persistence.repository.VentaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VentaService implements IVenta {

    @Autowired
    private VentaRepo ventaRepo;

    @Override
    public Page<VentaDTO> getHistorialVentas(Pageable pageable) {
        Page<Venta> ventas = ventaRepo.findAll(pageable);
        return ventas.map(this::convertToDto);
    }

    @Override
    public Page<Venta> getHistorialVentasPorUsuario(Long usuarioId, Pageable pageable) {
        return ventaRepo.findByUsuarioId(usuarioId, pageable);
    }
    @Override
    public void eliminarHistorialVentas(Long id) {
        ventaRepo.deleteById(id);
    }

    private VentaDTO convertToDto(Venta venta) {
        return new VentaDTO(
                venta.getId(),
                venta.getFechaVenta(),
                venta.getTotalVenta(),
                venta.getUsuario().getId() // Puedes incluir otros campos del usuario si lo deseas
        );
    }
}
