package com.Ecommerce.service;

import com.Ecommerce.model.CategoriaModel;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService {
    //funcionalidades /logica que  realizara esta entidad
    String crearCategoria(CategoriaModel categoria);
    List<CategoriaModel> listarCategorias();
    Optional<CategoriaModel> obtenerCategoriaPorId(int categoriaId);
    String eliminarCategoriaPorId(int categoriaId);
    String actualizarCategoriaPorId(CategoriaModel categoria);
}
