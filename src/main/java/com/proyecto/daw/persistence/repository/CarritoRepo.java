package com.proyecto.daw.persistence.repository;

import com.proyecto.daw.persistence.entity.Carrito;
import com.proyecto.daw.persistence.entity.Producto;
import com.proyecto.daw.persistence.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarritoRepo extends JpaRepository<Carrito, Long> {
    // Encuentra el carrito por usuario y producto
    Optional<Carrito> findByUsuarioAndProducto(Usuario usuario, Producto producto);
    // Encuentra el carrito por ID de usuario y ID de producto
    Optional<Carrito> findByUsuario_IdAndProducto_Id(Long userId, Long productoId);
    // Método para obtener el carrito por ID de usuario
    List<Carrito> findByUsuarioId(Long userId);
}
