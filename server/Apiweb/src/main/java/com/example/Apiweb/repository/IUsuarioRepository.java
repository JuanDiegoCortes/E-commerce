package com.example.Apiweb.repository;

import com.example.Apiweb.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<UsuarioModel, Integer> {
    @Query(value = "SELECT * FROM Usuario WHERE correo = :correo", nativeQuery = true)
    Optional<UsuarioModel> obtenerUsuarioPorCorreo(@Param("correo") String correo);
}
