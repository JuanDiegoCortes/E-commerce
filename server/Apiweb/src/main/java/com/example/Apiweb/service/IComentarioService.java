package com.example.Apiweb.service;

import com.example.Apiweb.model.ComentarioModel;

import java.util.List;
import java.util.Optional;

public interface IComentarioService {
    //funcionalidades /logica que  realizara esta entidad
    String crearComentario(ComentarioModel comentario);
    List<ComentarioModel> listarComentarios();
    Optional<ComentarioModel> obtenerComentarioPorId(int comentarioId);
    String eliminarComentarioPorId(int comentarioId);
    String actualizarComentarioPorId(ComentarioModel comentario);
}
