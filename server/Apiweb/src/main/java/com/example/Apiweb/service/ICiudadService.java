package com.example.Apiweb.service;

import com.example.Apiweb.model.CiudadModel;

import java.util.List;
import java.util.Optional;

public interface ICiudadService {
    //funcionalidades /logica que  realizara esta entidad
    String crearCiudad(CiudadModel ciudad);
    List<CiudadModel> listarCiudades();
    Optional<CiudadModel> obtenerCiudadPorId(int ciudadId);
    String eliminarCiudadPorId(int ciudadId);
    String actualizarCiudadPorId(CiudadModel ciudad);
}
