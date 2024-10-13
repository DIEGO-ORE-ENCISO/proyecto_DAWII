package com.proyecto.daw.controller;

import com.proyecto.daw.dto.VentaDTO;
import com.proyecto.daw.persistence.entity.Venta;
import com.proyecto.daw.services.impl.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    // Endpoint para listar todas las ventas
    @GetMapping("/listar")
    public ResponseEntity<Page<VentaDTO>> listarVentas(Pageable pageable) {
        Page<VentaDTO> ventas = ventaService.getHistorialVentas(pageable);
        return ResponseEntity.ok(ventas);
    }


    // Endpoint para listar ventas por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Page<Venta>> listarVentasPorUsuario(
            @PathVariable Long usuarioId,
            Pageable pageable) {
        Page<Venta> ventas = ventaService.getHistorialVentasPorUsuario(usuarioId, pageable);
        return ResponseEntity.ok(ventas);
    }

    // Endpoint para eliminar una venta
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> eliminarVenta(@PathVariable Long id) {
        try {
            ventaService.eliminarHistorialVentas(id);
            return ResponseEntity.ok("Venta eliminada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la venta: " + e.getMessage());
        }
    }




}
