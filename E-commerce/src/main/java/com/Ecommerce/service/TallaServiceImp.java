package com.Ecommerce.service;

import com.Ecommerce.model.EnvioModel;
import com.Ecommerce.model.TallaModel;
import com.Ecommerce.repository.ITallaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class TallaServiceImp implements ITallaService{
    @Autowired
    ITallaRepository tallaRepository;

    @Override
    public String crearTalla(TallaModel talla) {
        this.tallaRepository.save(talla);
        return "La talla " + talla.getIdTalla() + " fue creada exitosamente";
    }

    @Override
    public List<TallaModel> listarTallas() {
        return this.tallaRepository.findAll();
    }

    @Override
    public Optional<TallaModel> obtenerTallaPorId(int tallaId) {
        return this.tallaRepository.findById(tallaId);
    }

    @Override
    public String eliminarTallaPorId(int tallaId) {
        Optional<TallaModel> tallaRef = this.tallaRepository.findById(tallaId);
        this.tallaRepository.deleteById(tallaId);
        return "La talla " + tallaRef.get().getIdTalla() + " fue eliminada con exito.";
    }

    @Override
    public String actualizarTallaPorId(TallaModel talla) {
        this.tallaRepository.save(talla);
        return "La talla con id " + talla.getIdTalla() + " fue actualizada con exito.";
    }
}
