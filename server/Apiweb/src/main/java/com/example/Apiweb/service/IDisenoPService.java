package com.example.Apiweb.service;

import com.example.Apiweb.model.DisenoPModel;

import java.util.List;
import java.util.Optional;

public interface IDisenoPService {
    //funcionalidades /logica que  realizara esta entidad
    String crearDisenoP(DisenoPModel disenoP);
    List<DisenoPModel> listarDisenosP();
    Optional<DisenoPModel> obtenerDisenoPPorId(int disenoPId);
    String eliminarDisenoPPorId(int disenoPId);
    String actualizarDisenoPPorId(DisenoPModel disenoP);

    Optional<DisenoPModel> mostrarDisenoCompartidoPorIdOrden(int idDisenoP);
}
