package com.Ecommerce.service;

import com.Ecommerce.model.CiudadModel;
import com.Ecommerce.repository.ICiudadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class CiudadServiceImp implements ICiudadService{
    @Autowired
    ICiudadRepository ciudadRepository;

    @Override
    public String crearCiudad(CiudadModel ciudad) {
        this.ciudadRepository.save(ciudad);
        return "La ciudad " + ciudad.getNombre() + " fue creada exitosamente";
    }

    @Override
    public List<CiudadModel> listarCiudades() {
        return this.ciudadRepository.findAll();
    }

    @Override
    public Optional<CiudadModel> obtenerCiudadPorId(int ciudadId) {
        return this.ciudadRepository.findById(ciudadId);
    }

    @Override
    public String eliminarCiudadPorId(int ciudadId) {
        Optional<CiudadModel> ciudadRef = this.ciudadRepository.findById(ciudadId);
        this.ciudadRepository.deleteById(ciudadId);
        return "La ciudad " + ciudadRef.get().getNombre() + " fue eliminada con exito.";
    }

    @Override
    public String actualizarCiudadPorId(CiudadModel ciudad) {
        this.ciudadRepository.save(ciudad);
        return "La ciudad con id " + ciudad.getIdCiudad() + " fue actualizada con exito.";
    }
}
