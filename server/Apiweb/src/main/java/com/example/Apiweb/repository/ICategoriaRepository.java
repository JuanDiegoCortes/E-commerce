package com.example.Apiweb.repository;

import com.example.Apiweb.model.CategoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaRepository extends JpaRepository<CategoriaModel, Integer> {
}
