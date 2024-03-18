package com.example.Apiweb.service;

import com.example.Apiweb.model.ProdTallaModel;
import com.example.Apiweb.repository.IProdTallaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class ProdTallaServiceImp implements IProdTallaService{
    @Autowired
    IProdTallaRepository prodTallaRepository;

    @Override
    public String crearProdTalla(ProdTallaModel prodTalla) {
        this.prodTallaRepository.save(prodTalla);
        return "El prodTalla " + prodTalla.getIdProdTalla() + " fue creado exitosamente";
    }

    @Override
    public List<ProdTallaModel> listarProdTallas() {
        return this.prodTallaRepository.findAll();
    }

    @Override
    public Optional<ProdTallaModel> obtenerProdTallaPorId(int prodTallaId) {
        return this.prodTallaRepository.findById(prodTallaId);
    }

    @Override
    public String eliminarProdTallaPorId(int prodTallaId) {
        Optional<ProdTallaModel> prodTallaRef = this.prodTallaRepository.findById(prodTallaId);
        this.prodTallaRepository.deleteById(prodTallaId);
        return "La prodTalla " + prodTallaRef.get().getIdProdTalla() + " fue eliminada con exito.";
    }

    @Override
    public String actualizarProdTallaPorId(ProdTallaModel prodTalla) {
        this.prodTallaRepository.save(prodTalla);
        return "La prodTalla con id " + prodTalla.getIdProdTalla() + " fue actualizada con exito.";
    }
}
