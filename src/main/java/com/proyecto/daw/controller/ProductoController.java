package com.proyecto.daw.controller;


import com.proyecto.daw.dto.ProductoDTO;
import com.proyecto.daw.services.impl.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        return ResponseEntity.ok(productoService.getAllProductos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.getProductoById(id));
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> createProducto(@RequestBody ProductoDTO productoDTO) {
        return ResponseEntity.ok(productoService.saveProducto(productoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> updateProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        return ResponseEntity.ok(productoService.updateProducto(id, productoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }
}
