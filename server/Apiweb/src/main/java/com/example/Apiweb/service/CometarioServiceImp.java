package com.example.Apiweb.service;

import com.example.Apiweb.model.ComentarioModel;
import com.example.Apiweb.repository.IComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class CometarioServiceImp implements IComentarioService{
    @Autowired
    IComentarioRepository comentarioRepository;

    @Override
    public String crearComentario(ComentarioModel comentario) {
        this.comentarioRepository.save(comentario);
        return "El comentario " + comentario.getTexto() + " fue creado exitosamente";
    }

    @Override
    public List<ComentarioModel> listarComentarios() {
        return this.comentarioRepository.findAll();
    }

    @Override
    public Optional<ComentarioModel> obtenerComentarioPorId(int comentarioId) {
        return this.comentarioRepository.findById(comentarioId);
    }

    @Override
    public String eliminarComentarioPorId(int comentarioId) {
        Optional<ComentarioModel> comentarioRef = this.comentarioRepository.findById(comentarioId);
        this.comentarioRepository.deleteById(comentarioId);
        return "El cometario " + comentarioRef.get().getIdComentario() + " fue eliminado con exito.";
    }

    @Override
    public String actualizarComentarioPorId(ComentarioModel comentario) {
        this.comentarioRepository.save(comentario);
        return "El comentario con id " + comentario.getIdComentario() + " fue actualizado con exito.";
    }
}
