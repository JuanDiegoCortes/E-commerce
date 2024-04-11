package com.example.Apiweb.service;

import com.example.Apiweb.model.RolUsuarioModel;
import com.example.Apiweb.repository.IRolUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class RolUsuarioServiceImp implements IRolUsuarioService{
    @Autowired
    IRolUsuarioRepository rolUsuarioRepository;

    @Override
    public String crearRolUsuario(RolUsuarioModel rolUsuario) {
        this.rolUsuarioRepository.save(rolUsuario);
        return "El rolUsuario " + rolUsuario.getIdRolUsuario() + " fue creado exitosamente";
    }

    @Override
    public List<RolUsuarioModel> listarRolesUsuario() {
        return this.rolUsuarioRepository.findAll();
    }

    @Override
    public Optional<RolUsuarioModel> obtenerRolUsuarioPorId(int rolUsuarioId) {
        return this.rolUsuarioRepository.findById(rolUsuarioId);
    }

    @Override
    public String eliminarRolUsuarioPorId(int rolUsuarioId) {
        Optional<RolUsuarioModel> rolUsuarioRef = this.rolUsuarioRepository.findById(rolUsuarioId);
        this.rolUsuarioRepository.deleteById(rolUsuarioId);
        return "El rolUsuario " + rolUsuarioRef.get().getIdRolUsuario() + " fue eliminado con exito.";
    }

    @Override
    public String actualizarRolUsuarioPorId(RolUsuarioModel rolUsuario) {
        this.rolUsuarioRepository.save(rolUsuario);
        return "El rolUsuario con id " + rolUsuario.getIdRolUsuario() + " fue actualizado con exito.";
    }
}
