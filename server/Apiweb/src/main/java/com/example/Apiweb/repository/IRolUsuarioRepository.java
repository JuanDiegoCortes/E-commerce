package com.example.Apiweb.repository;

import com.example.Apiweb.model.RolUsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IRolUsuarioRepository extends JpaRepository<RolUsuarioModel, Integer> {
    @Query(value = "SELECT * " +
            "FROM RolUsuario ru " +
            "WHERE ru.cedula = :cedula AND ru.contrasena = :contrasena", nativeQuery = true)
    Optional<RolUsuarioModel> mostrarUsuarioPorCedulaYContrasena(@Param("cedula") Integer cedula, @Param("contrasena") String contrasena);

    @Query(value = "SELECT * " +
            "FROM RolUsuario ru " +
            "WHERE ru.cedula = :cedula", nativeQuery = true)
    Optional<RolUsuarioModel> obtenerRolUsuarioPorCedula(@Param("cedula") Integer cedula);
}
