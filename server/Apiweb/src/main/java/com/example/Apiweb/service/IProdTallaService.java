package com.example.Apiweb.service;

import com.example.Apiweb.model.ProdTallaModel;

import java.util.List;
import java.util.Optional;

public interface IProdTallaService {
    //funcionalidades /logica que  realizara esta entidad
    String crearProdTalla(ProdTallaModel prodTalla);
    List<ProdTallaModel> listarProdTallas();
    Optional<ProdTallaModel> obtenerProdTallaPorId(int prodTallaId);
    String eliminarProdTallaPorId(int prodTallaId);
    String actualizarProdTallaPorId(ProdTallaModel prodTalla);
    void actualizarCantidadProdTalla(int cantidad, int idProducto, int idTalla);
    ProdTallaModel obtenerProdTallaPorProductoYTalla(int idProducto, int idTalla);
    List<ProdTallaModel> obtenerProdTallaPorIdProducto(int idProducto);
}
