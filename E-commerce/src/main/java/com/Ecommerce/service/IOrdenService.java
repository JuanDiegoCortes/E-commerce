package com.Ecommerce.service;

import com.Ecommerce.model.EnvioModel;
import com.Ecommerce.model.OrdenModel;

import java.util.List;
import java.util.Optional;

public interface IOrdenService {
    //funcionalidades /logica que  realizara esta entidad
    String crearOrden(OrdenModel orden);
    List<OrdenModel> listarOrdenes();
    Optional<OrdenModel> obtenerOrdenPorId(int ordenId);
    String eliminarOrdenPorId(int ordenId);
    String actualizarOrdenPorId(OrdenModel orden);
}
