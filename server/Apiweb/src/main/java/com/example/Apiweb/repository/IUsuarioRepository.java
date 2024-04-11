package com.example.Apiweb.repository;

import com.example.Apiweb.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<UsuarioModel, Integer> {
    @Query(value = "SELECT ru.cedula, ru.idRol " +
            "FROM Usuario u " +
            "INNER JOIN RolUsuario ru ON u.cedula = ru.cedula " +
            "WHERE u.cedula = :cedula AND ru.contrasena = :contrasena", nativeQuery = true)
    Optional<UsuarioModel> mostrarUsuarioPorCedulaYContrasena(@Param("cedula") Integer cedula, @Param("contrasena") String contrasena);
}
