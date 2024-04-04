package com.example.Apiweb.service;

import com.example.Apiweb.model.EvidenciaPagoModel;
import com.example.Apiweb.repository.IEvidenciaPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class EvidenciaPagoServiceImp implements IEvidenciaPagoService {
    @Autowired
    IEvidenciaPagoRepository evidenciaPagoRepository;

    @Override
    public String crearEvidenciaPago(EvidenciaPagoModel evidenciaPago) {
        this.evidenciaPagoRepository.save(evidenciaPago);
        return "La evidencia de pago " + evidenciaPago.getIdEvidenciaPago() + " fue creada exitosamente";
    }

    @Override
    public List<EvidenciaPagoModel> listarEvidenciasPago() {
        return this.evidenciaPagoRepository.findAll();
    }

    @Override
    public Optional<EvidenciaPagoModel> obtenerEvidenciaPagoPorId(int evidenciaPagoId) {
        return this.evidenciaPagoRepository.findById(evidenciaPagoId);
    }

    @Override
    public String eliminarEvidenciaPagoPorId(int evidenciaPagoId) {
        Optional<EvidenciaPagoModel> evidenciaPagoRef = this.evidenciaPagoRepository.findById(evidenciaPagoId);
        this.evidenciaPagoRepository.deleteById(evidenciaPagoId);
        return "La evidencia de pago " + evidenciaPagoRef.get().getIdEvidenciaPago() + " fue eliminada con exito.";
    }

    @Override
    public String actualizarEvidenciaPagoPorId(EvidenciaPagoModel evidenciaPago) {
        this.evidenciaPagoRepository.save(evidenciaPago);
        return "La evidencia de pago con id " + evidenciaPago.getIdEvidenciaPago() + " fue actualizada con exito.";
    }
}
