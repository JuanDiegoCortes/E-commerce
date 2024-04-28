package com.example.Apiweb.service;

import com.example.Apiweb.model.OrdenModel;
import com.example.Apiweb.model.enums.Estado;

import java.util.List;
import java.util.Optional;

public interface IOrdenService {
    //funcionalidades /logica que  realizara esta entidad
    String crearOrden(OrdenModel orden);
    List<OrdenModel> listarOrdenes();
    Optional<OrdenModel> obtenerOrdenPorId(int ordenId);
    String eliminarOrdenPorId(int ordenId);
    String actualizarOrdenPorId(OrdenModel orden);
    String agregarEvidenciaPagoOrden(OrdenModel ordenIdEvidencia);
    List<OrdenModel> mostrarOrdenesPorCedula(int cedula);
    String actualizarEstadoOrden(int idOrden, Estado estado);
}
