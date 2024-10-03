package com.proyecto.daw.controller;

import com.proyecto.daw.dto.CategoriaDTO;
import com.proyecto.daw.exceptions.CategoriaNotFoundException;
import com.proyecto.daw.services.impl.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria") // Asegúrate de que la ruta esté bien definida
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias() {
        System.out.println("Recibida solicitud para obtener todas las categorías");
        List<CategoriaDTO> categorias = categoriaService.getAllCategorias();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> getCategoriaById(@PathVariable Long id) throws CategoriaNotFoundException {
        CategoriaDTO categoria = categoriaService.getCategoriaById(id);
        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> createCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        System.out.println("Recibida solicitud para crear categoría: " + categoriaDTO);
        CategoriaDTO nuevaCategoria = categoriaService.saveCategoria(categoriaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria); // Cambié a CREATED para la respuesta
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> updateCategoria(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) throws CategoriaNotFoundException {
        CategoriaDTO categoriaActualizada = categoriaService.updateCategoria(id, categoriaDTO);
        return ResponseEntity.ok(categoriaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) throws CategoriaNotFoundException {
        categoriaService.deleteCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
