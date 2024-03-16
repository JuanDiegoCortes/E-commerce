package com.Ecommerce.service;

import com.Ecommerce.model.CiudDeptoModel;

import java.util.List;
import java.util.Optional;

public interface ICiudDeptoService {
    //funcionalidades logica que  realizara esta entidad
    String crearCiudDepto(CiudDeptoModel ciudDepto);
    List<CiudDeptoModel> listarCiudDepto();
    Optional<CiudDeptoModel> obtenerCiudDeptoPorId(int ciudDeptoId);
    String eliminarCategoriaPorId(int ciudDeptoId);
    String actualizarCategoriaPorId(CiudDeptoModel ciudDepto);
}
