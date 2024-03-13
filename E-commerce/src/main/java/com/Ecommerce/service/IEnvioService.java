package com.Ecommerce.service;

import com.Ecommerce.model.EnvioModel;

import java.util.List;
import java.util.Optional;

public interface IEnvioService {
    //funcionalidades /logica que  realizara esta entidad
    String crearEnvio(EnvioModel envio);
    List<EnvioModel> listarEnvios();
    Optional<EnvioModel> obtenerEnvioPorId(int envioId);
    String eliminarEnvioPorId(int envioId);
    String actualizarEnvioPorId(EnvioModel envio);
}
