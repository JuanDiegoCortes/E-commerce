package com.Ecommerce.service;

import com.Ecommerce.model.ProdTallaModel;

import java.util.List;
import java.util.Optional;

public interface IProdTallaService {
    //funcionalidades /logica que  realizara esta entidad
    String crearProdTalla(ProdTallaModel prodTalla);
    List<ProdTallaModel> listarProdTallas();
    Optional<ProdTallaModel> obtenerProdTallaPorId(int prodTallaId);
    String eliminarProdTallaPorId(int prodTallaId);
    String actualizarProdTallaPorId(ProdTallaModel prodTalla);
}
