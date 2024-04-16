package com.example.Apiweb.service;

import com.example.Apiweb.model.OrdenProdModel;
import com.example.Apiweb.repository.IOrdenProdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class OrdenProdServiceImp implements IOrdenProdService {
    @Autowired
    IOrdenProdRepository ordenProdRepository;
    @Override
    public String crearOrdenProd(OrdenProdModel ordenProd) {
        this.ordenProdRepository.save(ordenProd);
        return "La ordenProd " + ordenProd.getIdOrdenProd() + " fue creada exitosamente";
    }

    @Override
    public List<OrdenProdModel> listarOrdenProds() {
        return this.ordenProdRepository.findAll();
    }

    @Override
    public Optional<OrdenProdModel> obtenerOrdenProdPorId(int ordenProdId) {
        return this.ordenProdRepository.findById(ordenProdId);
    }

    @Override
    public String eliminarOrdenProdPorId(int ordenProdId) {
        Optional<OrdenProdModel> ordenProdRef = this.ordenProdRepository.findById(ordenProdId);
        this.ordenProdRepository.deleteById(ordenProdId);
        return "La ordenProd " + ordenProdRef.get().getIdOrdenProd() + " fue eliminada con exito.";
    }

    @Override
    public String actualizarOrdenProdPorId(OrdenProdModel ordenProd) {
        this.ordenProdRepository.save(ordenProd);
        return "El ordenProd con id " + ordenProd.getIdOrden() + " fue actualizada con exito.";
    }

    @Override
    public List<OrdenProdModel> mostrarProductosPorIdOrden(int idOrden) {
        return this.ordenProdRepository.listarProductosPorIdOrden(idOrden);
    }
}
