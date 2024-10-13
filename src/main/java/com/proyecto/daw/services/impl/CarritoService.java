package com.proyecto.daw.services.impl;

import com.proyecto.daw.dto.CarritoDTO;
import com.proyecto.daw.persistence.entity.Carrito;
import com.proyecto.daw.persistence.entity.Producto;
import com.proyecto.daw.persistence.entity.Usuario;
import com.proyecto.daw.persistence.interfaces.ICarrito;
import com.proyecto.daw.persistence.repository.CarritoRepo;
import com.proyecto.daw.persistence.repository.ProductoRepository;
import com.proyecto.daw.persistence.repository.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarritoService implements ICarrito {

    @Autowired
    private CarritoRepo carritoRepo;

    @Autowired
    private ProductoRepository productoRepo;

    @Autowired
    private UsuarioRepo usuarioRepo;


    @Override
    public void agregarProducto(Long userId, Long productoId, int cantidad) {
        Usuario usuario = usuarioRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Producto producto = productoRepo.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Verificar si el producto ya está en el carrito
        Carrito carrito = carritoRepo.findByUsuarioAndProducto(usuario, producto).orElse(null);
        if (carrito != null) {
            // Si ya existe, solo aumentamos la cantidad
            carrito.setCantidad(carrito.getCantidad() + cantidad);
            carrito.setTotalCarrito(carrito.getCantidad() * producto.getPrecio());
        } else {
            // Si no existe, creamos un nuevo carrito
            carrito = new Carrito();
            carrito.setUsuario(usuario);
            carrito.setProducto(producto);
            carrito.setCantidad(cantidad);
            carrito.setTotalCarrito(cantidad * producto.getPrecio());
        }

        carritoRepo.save(carrito);
    }

    @Override
    public List<CarritoDTO> listarProductos(Long userId) {
        List<Carrito> carritoList = carritoRepo.findByUsuarioId(userId);
        return carritoList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void aumentarCantidad(Long carritoId) {
        Carrito carrito = carritoRepo.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        carrito.setCantidad(carrito.getCantidad() + 1); // Aumenta la cantidad en 1
        carrito.setTotalCarrito(carrito.getCantidad() * carrito.getProducto().getPrecio());
        carritoRepo.save(carrito);
    }

    @Override
    public void disminuirCantidad(Long carritoId) {
        Carrito carrito = carritoRepo.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        Producto producto = carrito.getProducto();

        if (carrito.getCantidad() > 1) {
            carrito.setCantidad(carrito.getCantidad() - 1);
            carrito.setTotalCarrito(carrito.getCantidad() * producto.getPrecio());
            carritoRepo.save(carrito);
        } else {
            eliminarCarrito(carritoId);
        }
    }

    @Override
    public void eliminarCarrito(Long carritoId) {
        Carrito carrito = carritoRepo.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        carritoRepo.delete(carrito);
    }


    private CarritoDTO mapToDTO(Carrito carrito) {
        CarritoDTO dto = new CarritoDTO();
        dto.setIdCarrito(carrito.getId());
        dto.setIdProducto(carrito.getProducto().getId());
        dto.setCantidad(carrito.getCantidad());
        dto.setTotalCarrito(carrito.getTotalCarrito());
        dto.setIdUser(carrito.getUsuario().getId());
        return dto;
    }
}
