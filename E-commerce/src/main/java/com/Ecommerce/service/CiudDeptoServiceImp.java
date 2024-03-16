package com.Ecommerce.service;

import com.Ecommerce.model.CategoriaModel;
import com.Ecommerce.model.CiudDeptoModel;
import com.Ecommerce.repository.ICiudDeptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class CiudDeptoServiceImp implements ICiudDeptoService{
    @Autowired
    ICiudDeptoRepository ciudDeptoRepository;

    @Override
    public String crearCiudDepto(CiudDeptoModel ciudDepto) {
        this.ciudDeptoRepository.save(ciudDepto);
        return "La ciudDepto " + ciudDepto.getIdCiudDepto() + " fue creada exitosamente";
    }

    @Override
    public List<CiudDeptoModel> listarCiudDepto() {
        return this.ciudDeptoRepository.findAll();
    }

    @Override
    public Optional<CiudDeptoModel> obtenerCiudDeptoPorId(int ciudDeptoId) {
        return this.ciudDeptoRepository.findById(ciudDeptoId);
    }

    @Override
    public String eliminarCategoriaPorId(int ciudDeptoId) {
        Optional<CiudDeptoModel> ciudDeptoRef = this.ciudDeptoRepository.findById(ciudDeptoId);
        this.ciudDeptoRepository.deleteById(ciudDeptoId);
        return "La ciudDepto " + ciudDeptoRef.get().getIdCiudDepto() + " fue eliminada con exito.";
    }

    @Override
    public String actualizarCategoriaPorId(CiudDeptoModel ciudDepto) {
        this.ciudDeptoRepository.save(ciudDepto);
        return "El ciudDepto con id " + ciudDepto.getIdCiudDepto() + " fue actualizada con exito.";
    }
}
