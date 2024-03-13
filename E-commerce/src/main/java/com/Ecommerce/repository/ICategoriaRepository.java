package com.Ecommerce.repository;

import com.Ecommerce.model.CategoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaRepository extends JpaRepository<CategoriaModel, Integer> {
}
