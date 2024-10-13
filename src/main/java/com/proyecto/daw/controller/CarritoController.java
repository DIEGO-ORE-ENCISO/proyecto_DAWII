package com.proyecto.daw.controller;

import com.proyecto.daw.dto.CarritoDTO;
import com.proyecto.daw.services.impl.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    // Endpoint para agregar un producto al carrito
    @PostMapping("/agregar/{userId}/{productoId}/{cantidad}")
    public ResponseEntity<String> agregarProducto(
            @PathVariable Long userId,
            @PathVariable Long productoId,
            @PathVariable int cantidad) {
        carritoService.agregarProducto(userId, productoId, cantidad);
        return ResponseEntity.ok("Producto agregado al carrito");
    }

    // Endpoint para listar los productos en el carrito
    @GetMapping("/{userId}")
    public ResponseEntity<List<CarritoDTO>> listarProductos(@PathVariable Long userId) {
        List<CarritoDTO> productosCarrito = carritoService.listarProductos(userId);
        return ResponseEntity.ok(productosCarrito);
    }

    // Endpoint para aumentar la cantidad de un producto en el carrito
    @PutMapping("/aumentar/{carritoId}")
    public ResponseEntity<String> aumentarCantidad(
            @PathVariable Long carritoId) {
        try {
            carritoService.aumentarCantidad(carritoId); // Llama al servicio sin cantidad
            return ResponseEntity.ok("Cantidad aumentada en el carrito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al aumentar la cantidad: " + e.getMessage());
        }
    }

    @PutMapping("/disminuir/{carritoId}")
    public ResponseEntity<String> disminuirCantidad(
            @PathVariable Long carritoId) {
        try {
            carritoService.disminuirCantidad(carritoId); // Llama al servicio sin cantidad
            return ResponseEntity.ok("Cantidad disminuida en el carrito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al disminuir la cantidad: " + e.getMessage());
        }
    }

    // Endpoint para eliminar un producto del carrito
    @DeleteMapping("/eliminar/{carritoId}")
    public ResponseEntity<String> eliminarCarrito(
            @PathVariable Long carritoId) {
        try {
            carritoService.eliminarCarrito(carritoId);
            return ResponseEntity.ok("Carrito eliminado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el carrito: " + e.getMessage());
        }
    }

}
