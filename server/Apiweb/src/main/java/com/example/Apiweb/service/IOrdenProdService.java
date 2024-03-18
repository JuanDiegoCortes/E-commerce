package com.Ecommerce.service;

import com.Ecommerce.model.OrdenProdModel;

import java.util.List;
import java.util.Optional;

public interface IOrdenProdService {
    //funcionalidades /logica que  realizara esta entidad
    String crearOrdenProd(OrdenProdModel ordenProd);
    List<OrdenProdModel> listarOrdenProds();
    Optional<OrdenProdModel> obtenerOrdenProdPorId(int ordenProdId);
    String eliminarOrdenProdPorId(int ordenProdId);
    String actualizarOrdenProdPorId(OrdenProdModel ordenProd);
}
