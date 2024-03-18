package com.Ecommerce.service;

import com.Ecommerce.model.TallaModel;

import java.util.List;
import java.util.Optional;

public interface ITallaService {
    //funcionalidades /logica que  realizara esta entidad
    String crearTalla(TallaModel talla);
    List<TallaModel> listarTallas();
    Optional<TallaModel> obtenerTallaPorId(int tallaId);
    String eliminarTallaPorId(int tallaId);
    String actualizarTallaPorId(TallaModel talla);
}
