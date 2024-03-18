package com.Ecommerce.repository;

import com.Ecommerce.model.ComentarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IComentarioRepository extends JpaRepository<ComentarioModel, Integer> {
}
