package com.example.Apiweb.service;

import com.example.Apiweb.model.EvidenciaPagoModel;

import java.util.List;
import java.util.Optional;

public interface IEvidenciaPagoService {
    //funcionalidades logica que  realizara esta entidad
    String crearEvidenciaPago(EvidenciaPagoModel evidenciaPago);
    List<EvidenciaPagoModel> listarEvidenciasPago();
    Optional<EvidenciaPagoModel> obtenerEvidenciaPagoPorId(int evidenciaPagoId);
    String eliminarEvidenciaPagoPorId(int evidenciaPagoId);
    String actualizarEvidenciaPagoPorId(EvidenciaPagoModel evidenciaPago);
}
