package com.proyecto.daw.persistence.interfaces;

import com.proyecto.daw.dto.CarritoDTO;

import java.util.List;

public interface ICarrito {

    // Agregar un producto al carrito
    void agregarProducto(Long userId, Long productoId, int cantidad);

    // Listar los productos del carrito
    List<CarritoDTO> listarProductos(Long userId);

    // Aumentar la cantidad de un producto en el carrito
    public void aumentarCantidad(Long carritoId);

    // Disminuir la cantidad de un producto en el carrito
    public void disminuirCantidad(Long carritoId);

    // Eliminar un producto del carrito
    void eliminarCarrito(Long carritoId);
}