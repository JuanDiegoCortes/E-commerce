package com.example.Apiweb.service;

import com.example.Apiweb.model.RolUsuarioModel;

import java.util.List;
import java.util.Optional;

public interface IRolUsuarioService {
    //funcionalidades /logica que  realizara esta entidad
    String crearRolUsuario(RolUsuarioModel rolUsuario);
    List<RolUsuarioModel> listarRolesUsuario();
    Optional<RolUsuarioModel> obtenerRolUsuarioPorId(int rolUsuarioId);
    String eliminarRolUsuarioPorId(int rolUsuarioId);
    String actualizarRolUsuarioPorId(RolUsuarioModel rolUsuario);
    Optional<RolUsuarioModel> verUsuarioPorCedulaYContrasena(int cedula, String contrasena);
    Optional<RolUsuarioModel> mostrarRolUsuarioPorCedula(int cedula);
}
