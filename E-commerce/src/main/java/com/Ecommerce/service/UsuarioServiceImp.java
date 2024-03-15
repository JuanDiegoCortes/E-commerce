package com.Ecommerce.service;

import com.Ecommerce.model.UsuarioModel;
import com.Ecommerce.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class UsuarioServiceImp implements IUsuarioService{
    @Autowired
    IUsuarioRepository usuarioRepository;

    @Override
    public String crearUsuario(UsuarioModel usuario) {
        this.usuarioRepository.save(usuario);
        return "El usuario " + usuario.getCedula() + " fue creado exitosamente";
    }

    @Override
    public List<UsuarioModel> listarUsuarios() {
        return this.usuarioRepository.findAll();
    }

    @Override
    public Optional<UsuarioModel> obtenerUsuarioPorId(int usuarioId) {
        return this.usuarioRepository.findById(usuarioId);
    }

    @Override
    public String eliminarUsuarioPorId(int usuarioId) {
        Optional<UsuarioModel> usuarioRef = this.usuarioRepository.findById(usuarioId);
        this.usuarioRepository.deleteById(usuarioId);
        return "El usuario " + usuarioRef.get().getCedula() + " fue eliminado con exito.";
    }

    @Override
    public String actualizarUsuarioPorId(UsuarioModel usuario) {
        this.usuarioRepository.save(usuario);
        return "El usuario con cédula " + usuario.getCedula() + " fue actualizado con exito.";
    }
}
