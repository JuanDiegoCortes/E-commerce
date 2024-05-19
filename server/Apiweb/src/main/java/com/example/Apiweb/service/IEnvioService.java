package com.example.Apiweb.service;

import com.example.Apiweb.model.EnvioModel;

import java.util.List;
import java.util.Optional;

public interface IEnvioService {
    //funcionalidades /logica que  realizara esta entidad
    EnvioModel crearEnvio(EnvioModel envio);
    List<EnvioModel> listarEnvios();
    Optional<EnvioModel> obtenerEnvioPorId(int envioId);
    String eliminarEnvioPorId(int envioId);
    String actualizarEnvioPorId(EnvioModel envio);
}
