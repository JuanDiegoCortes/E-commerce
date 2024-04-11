package com.example.Apiweb.service;

import com.example.Apiweb.model.UsuarioModel;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    //funcionalidades /logica que  realizara esta entidad
    String crearUsuario(UsuarioModel usuario);
    List<UsuarioModel> listarUsuarios();
    Optional<UsuarioModel> obtenerUsuarioPorId(int usuarioId);
    String eliminarUsuarioPorId(int usuarioId);
    String actualizarUsuarioPorId(UsuarioModel usuario);
    List<Object> verUsuarioPorCedulaYContrasena(int cedula, String contrasena);
}
