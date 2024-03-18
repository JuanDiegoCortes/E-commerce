package com.example.Apiweb.service;

import com.example.Apiweb.model.CategoriaModel;
import com.example.Apiweb.repository.ICategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class CategoriaServiceImp implements ICategoriaService{
    @Autowired
    ICategoriaRepository categoriaRepository;

    @Override
    public String crearCategoria(CategoriaModel categoria) {
        this.categoriaRepository.save(categoria);
        return "La categoria " + categoria.getNombre() + " fue creada exitosamente";
    }

    @Override
    public List<CategoriaModel> listarCategorias() {
        return this.categoriaRepository.findAll();
    }

    @Override
    public Optional<CategoriaModel> obtenerCategoriaPorId(int categoriaId) {
        return this.categoriaRepository.findById(categoriaId);
    }

    @Override
    public String eliminarCategoriaPorId(int categoriaId) {
        Optional<CategoriaModel> categoriaRef = this.categoriaRepository.findById(categoriaId);
        this.categoriaRepository.deleteById(categoriaId);
        return "La categoria " + categoriaRef.get().getNombre() + " fue eliminada con exito.";
    }

    @Override
    public String actualizarCategoriaPorId(CategoriaModel categoria) {
        this.categoriaRepository.save(categoria);
        return "El departamento con id " + categoria.getIdCategoria() + " fue actualizada con exito.";
    }
}
