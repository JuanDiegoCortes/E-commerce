package com.example.Apiweb.service;

import com.example.Apiweb.model.CategoriaModel;

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
