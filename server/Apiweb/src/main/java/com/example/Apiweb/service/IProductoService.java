package com.example.Apiweb.service;

import com.example.Apiweb.model.ProductoModel;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    //funcionalidades /logica que  realizara esta entidad
    String crearProducto(ProductoModel producto);
    List<ProductoModel> listarProductos();
    Optional<ProductoModel> obtenerProductoPorId(int productoId);
    String eliminarProductoPorId(int productoId);
    String actualizarProductoPorId(ProductoModel producto);
}
