package com.example.Apiweb.service;

import com.example.Apiweb.model.OrdenModel;
import com.example.Apiweb.model.enums.Estado;
import com.example.Apiweb.repository.IOrdenRepository;
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

    @Override
    public String agregarEvidenciaPagoOrden(OrdenModel ordenIdEvidencia) {
        this.ordenRepository.save(ordenIdEvidencia);
        return "La orden con id " + ordenIdEvidencia.getIdOrden() + " fue actualizada con exito.";
    }

    @Override
    public List<OrdenModel> mostrarOrdenesPorCedula(int cedula) {
        return this.ordenRepository.listarOrdenesPorCedula(cedula);
    }

    @Override
    public String actualizarEstadoOrden(int idOrden, Estado estado) {
        Optional<OrdenModel> ordenRef = this.ordenRepository.findById(idOrden);
        ordenRef.get().setEstado(estado);
        this.ordenRepository.save(ordenRef.get());
        return "El estado de la orden con id " + ordenRef.get().getIdOrden() + " fue actualizado con exito.";
    }

    @Override
    public Integer asignarDisenador(int idOrden, int disenadorAsginado) {
        Optional<OrdenModel> ordenRef = this.ordenRepository.findById(idOrden);
        ordenRef.get().setDisenadorAsignado(disenadorAsginado);
        this.ordenRepository.save(ordenRef.get());
        return(disenadorAsginado);
    }
}
