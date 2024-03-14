package com.Ecommerce.service;

import com.Ecommerce.model.UsuarioModel;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    //funcionalidades /logica que  realizara esta entidad
    String crearUsuario(UsuarioModel envio);
    List<UsuarioModel> listarUsuarios();
    Optional<UsuarioModel> obtenerUsuarioPorId(int usuarioId);
    String eliminarUsuarioPorId(int usuarioId);
    String actualizarUsuarioPorId(UsuarioModel usuario);
}
