package com.example.Apiweb.service;

import com.example.Apiweb.model.DepartamentoModel;

import java.util.List;
import java.util.Optional;

public interface IDepartamentoService {
    //funcionalidades /logica que  realizara esta entidad
    String crearDepartamento(DepartamentoModel departamento);
    List<DepartamentoModel> listarDepartamentos();
    Optional<DepartamentoModel> obtenerDepartamentoPorId(int departamentoId);
    String eliminarDepartamentoPorId(int departamentoId);
    String actualizarDepartamentoPorId(DepartamentoModel departamento);
}
