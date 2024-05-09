package com.example.Apiweb.service;

import com.example.Apiweb.model.OrdenProdModel;
import com.example.Apiweb.model.UsuarioModel;

import java.util.List;
import java.util.Optional;

public interface IOrdenProdService {
    //funcionalidades /logica que  realizara esta entidad
    String crearOrdenProd(OrdenProdModel ordenProd);
    List<OrdenProdModel> listarOrdenProds();
    Optional<OrdenProdModel> obtenerOrdenProdPorId(int ordenProdId);
    String eliminarOrdenProdPorId(int ordenProdId);
    String actualizarOrdenProdPorId(OrdenProdModel ordenProd);
    List<OrdenProdModel> mostrarProductosPorIdOrden(int idOrden);
    Integer asignarDisenador(int idOrdenProd, int disenadorAsignado);
}
