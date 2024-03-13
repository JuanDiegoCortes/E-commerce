package com.Ecommerce.service;

import com.Ecommerce.model.DiseñoPModel;
import com.Ecommerce.repository.IDiseñoPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class DiseñoPServiceImp implements IDiseñoPService{
    @Autowired
    IDiseñoPRepository diseñoPRepository;

    @Override
    public String crearDiseñoP(DiseñoPModel diseñoP) {
        this.diseñoPRepository.save(diseñoP);
        return "El diseñoP " + diseñoP.getIdDiseñoP() + " fue creado exitosamente";
    }

    @Override
    public List<DiseñoPModel> listarDiseñosP() {
        return this.diseñoPRepository.findAll();
    }

    @Override
    public Optional<DiseñoPModel> obtenerDiseñoPPorId(int diseñoPId) {
        return this.diseñoPRepository.findById(diseñoPId);
    }

    @Override
    public String eliminarDiseñoPPorId(int diseñoPId) {
        Optional<DiseñoPModel> diseñoPRef = this.diseñoPRepository.findById(diseñoPId);
        this.diseñoPRepository.deleteById(diseñoPId);
        return "El diseñoP " + diseñoPRef.get().getIdDiseñoP() + " fue eliminado con exito.";
    }

    @Override
    public String actualizarDiseñoPPorId(DiseñoPModel diseñoP) {
        this.diseñoPRepository.save(diseñoP);
        return "El diseñoP con id " + diseñoP.getIdDiseñoP() + " fue actualizado con exito.";
    }
}
