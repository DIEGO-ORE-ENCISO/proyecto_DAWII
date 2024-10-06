package com.proyecto.daw.services.impl;
import com.proyecto.daw.dto.ProductoDTO;
import com.proyecto.daw.persistence.entity.CategoriaProd;
import com.proyecto.daw.persistence.entity.Producto;
import com.proyecto.daw.persistence.repository.CategoriaRepository;
import com.proyecto.daw.persistence.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<ProductoDTO> getAllProductos() {
        return productoRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public ProductoDTO getProductoById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return mapToDTO(producto);
    }

    public ProductoDTO saveProducto(ProductoDTO productoDTO) {
        CategoriaProd categoria = categoriaRepository.findById(productoDTO.getCategoriaProdId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Producto producto = mapToEntity(productoDTO);
        producto.setCategoriaProd(categoria); // Asegúrate de que aquí no falta nada
        Producto nuevoProducto = productoRepository.save(producto);
        return mapToDTO(nuevoProducto);
    }

    public ProductoDTO updateProducto(Long id, ProductoDTO productoDTO) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Asigna los valores del DTO al objeto Producto
        producto.setNombreProducto(productoDTO.getNombreProducto());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setEstadoProducto(productoDTO.getEstadoProducto());
        producto.setStock(productoDTO.getStock()); // Asegúrate de asignar el stock
        producto.setImagen(productoDTO.getImagen()); // Asegúrate de asignar la imagen

        // Actualiza la categoría
        CategoriaProd categoria = categoriaRepository.findById(productoDTO.getCategoriaProdId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        producto.setCategoriaProd(categoria);

        // Guarda el producto actualizado
        Producto productoActualizado = productoRepository.save(producto);
        return mapToDTO(productoActualizado);
    }


    public void deleteProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productoRepository.delete(producto);
    }

    private ProductoDTO mapToDTO(Producto producto) {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(producto.getId());
        productoDTO.setNombreProducto(producto.getNombreProducto());
        productoDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setEstadoProducto(producto.getEstadoProducto());
        productoDTO.setStock(producto.getStock());
        productoDTO.setImagen(producto.getImagen()); // Asegúrate de incluir esta línea
        productoDTO.setCategoriaProdId(producto.getCategoriaProd().getId());
        return productoDTO;
    }



    private Producto mapToEntity(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setNombreProducto(productoDTO.getNombreProducto());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setEstadoProducto(productoDTO.getEstadoProducto());
        producto.setStock(productoDTO.getStock()); // Asegúrate de incluir esta línea si es necesario
        producto.setImagen(productoDTO.getImagen()); // Asegúrate de incluir esta línea
        return producto;
    }
}
