package com.example.Apiweb.service;

import com.example.Apiweb.model.RolModel;

import java.util.List;
import java.util.Optional;

public interface IRolService {
    //funcionalidades /logica que  realizara esta entidad
    String crearRol(RolModel rol);
    List<RolModel> listarRoles();
    Optional<RolModel> obtenerRolPorId(int rolId);
    String eliminarRolPorId(int rolId);
    String actualizarRolPorId(RolModel rol);
}
