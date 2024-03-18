package com.example.Apiweb.service;

import com.example.Apiweb.model.DepartamentoModel;
import com.example.Apiweb.repository.IDepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class DepartamentoServiceImp implements IDepartamentoService{
    @Autowired
    IDepartamentoRepository departamentoRepository;

    @Override
    public String crearDepartamento(DepartamentoModel departamento) {
        this.departamentoRepository.save(departamento);
        return "El departamento " + departamento.getNombre() + " fue creado exitosamente";
    }

    @Override
    public List<DepartamentoModel> listarDepartamentos() {
        return this.departamentoRepository.findAll();
    }

    @Override
    public Optional<DepartamentoModel> obtenerDepartamentoPorId(int departamentoId) {
        return this.departamentoRepository.findById(departamentoId);
    }

    @Override
    public String eliminarDepartamentoPorId(int departamentoId) {
        //recuperamos el departamento como una copia
        Optional<DepartamentoModel> departamentoRef = this.departamentoRepository.findById(departamentoId);
        this.departamentoRepository.deleteById(departamentoId);
        return "El departamento " + departamentoRef.get().getNombre() + " fue eliminado con exito.";
    }

    @Override
    public String actualizarDepartamentoPorId(DepartamentoModel departamento) {
        this.departamentoRepository.save(departamento);
        return "El departamento con id " + departamento.getIdDepartamento() + " fue actualizado con exito.";
    }
}
