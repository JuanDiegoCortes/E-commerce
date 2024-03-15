package com.Ecommerce.service;

import com.Ecommerce.model.RolModel;
import com.Ecommerce.repository.IRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class RolServiceImp implements IRolService{
    @Autowired
    IRolRepository rolRepository;

    @Override
    public String crearRol(RolModel rol) {
        this.rolRepository.save(rol);
        return "El rol " + rol.getIdRol() + " fue creado exitosamente";
    }

    @Override
    public List<RolModel> listarRol() {
        return this.rolRepository.findAll();
    }

    @Override
    public Optional<RolModel> obtenerRolPorId(int rolId) {
        return this.rolRepository.findById(rolId);
    }

    @Override
    public String eliminarRolPorId(int rolId) {
        Optional<RolModel> rolRef = this.rolRepository.findById(rolId);
        this.rolRepository.deleteById(rolId);
        return "El rol " + rolRef.get().getIdRol() + " fue eliminado con exito.";
    }

    @Override
    public String actualizarRolPorId(RolModel rol) {
        this.rolRepository.save(rol);
        return "El rol con id " + rol.getIdRol() + " fue actualizado con exito.";
    }


}
