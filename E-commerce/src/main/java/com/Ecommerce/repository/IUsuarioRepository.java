package com.Ecommerce.repository;

import com.Ecommerce.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<UsuarioModel, Integer> {
}
