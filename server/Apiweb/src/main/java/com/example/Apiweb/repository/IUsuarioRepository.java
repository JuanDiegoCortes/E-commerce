package com.example.Apiweb.repository;

import com.example.Apiweb.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<UsuarioModel, Integer> {
}
