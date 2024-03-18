package com.Ecommerce.service;

import com.Ecommerce.model.OrdenModel;
import com.Ecommerce.repository.IOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class OrdenServiceImp implements IOrdenService{
    @Autowired
    IOrdenRepository ordenRepository;

    @Override
    public String crearOrden(OrdenModel orden) {
        this.ordenRepository.save(orden);
        return "La orden " + orden.getIdOrden() + " fue creada exitosamente";
    }

    @Override
    public List<OrdenModel> listarOrdenes() {
        return this.ordenRepository.findAll();
    }

    @Override
    public Optional<OrdenModel> obtenerOrdenPorId(int ordenId) {
        return this.ordenRepository.findById(ordenId);
    }

    @Override
    public String eliminarOrdenPorId(int ordenId) {
        Optional<OrdenModel> ordenRef = this.ordenRepository.findById(ordenId);
        this.ordenRepository.deleteById(ordenId);
        return "La orden " + ordenRef.get().getIdOrden() + " fue eliminada con exito.";
    }

    @Override
    public String actualizarOrdenPorId(OrdenModel orden) {
        this.ordenRepository.save(orden);
        return "La orden con id " + orden.getIdOrden() + " fue actualizada con exito.";
    }
}
