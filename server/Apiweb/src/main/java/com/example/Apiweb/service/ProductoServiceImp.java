package com.example.Apiweb.service;

import com.example.Apiweb.model.ProductoModel;
import com.example.Apiweb.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class ProductoServiceImp implements IProductoService{
    @Autowired
    IProductoRepository productoRepository;

    @Override
    public String crearProducto(ProductoModel producto) {
        this.productoRepository.save(producto);
        return "El producto " + producto.getIdProducto() + " fue creado exitosamente";
    }

    @Override
    public List<ProductoModel> listarProductos() {
        return this.productoRepository.findAll();
    }

    @Override
    public Optional<ProductoModel> obtenerProductoPorId(int productoId) {
        return this.productoRepository.findById(productoId);
    }

    @Override
    public String eliminarProductoPorId(int productoId) {
        Optional<ProductoModel> productoRef = this.productoRepository.findById(productoId);
        this.productoRepository.deleteById(productoId);
        return "El produto " + productoRef.get().getIdProducto() + " fue eliminado con exito.";
    }

    @Override
    public String actualizarProductoPorId(ProductoModel producto) {
        this.productoRepository.save(producto);
        return "El producto con id " + producto.getIdProducto() + " fue actualizado con exito.";
    }
}
