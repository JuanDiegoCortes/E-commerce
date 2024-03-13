package com.Ecommerce.service;

import com.Ecommerce.model.DiseñoPModel;

import java.util.List;
import java.util.Optional;

public interface IDiseñoPService {
    //funcionalidades /logica que  realizara esta entidad
    String crearDiseñoP(DiseñoPModel diseñoP);
    List<DiseñoPModel> listarDiseñosP();
    Optional<DiseñoPModel> obtenerDiseñoPPorId(int diseñoPId);
    String eliminarDiseñoPPorId(int diseñoPId);
    String actualizarDiseñoPPorId(DiseñoPModel diseñoP);
}
