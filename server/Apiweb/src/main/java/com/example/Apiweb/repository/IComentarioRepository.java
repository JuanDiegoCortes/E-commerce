package com.example.Apiweb.repository;

import com.example.Apiweb.model.ComentarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IComentarioRepository extends JpaRepository<ComentarioModel, Integer> {
}
