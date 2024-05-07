package com.example.Apiweb.service;

import com.example.Apiweb.model.DisenoPModel;
import com.example.Apiweb.repository.IDisenoPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class DisenoPServiceImp implements IDisenoPService{
    @Autowired
    IDisenoPRepository disenoPRepository;

    @Override
    public String crearDisenoP(DisenoPModel disenoP) {
        this.disenoPRepository.save(disenoP);
        return "El diseñoP " + disenoP.getIdDisenoP() + " fue creado exitosamente";
    }

    @Override
    public List<DisenoPModel> listarDisenosP() {
        return this.disenoPRepository.findAll();
    }

    @Override
    public Optional<DisenoPModel> obtenerDisenoPPorId(int disenoPId) {
        return this.disenoPRepository.findById(disenoPId);
    }

    @Override
    public String eliminarDisenoPPorId(int disenoPId) {
        Optional<DisenoPModel> disenoPRef = this.disenoPRepository.findById(disenoPId);
        this.disenoPRepository.deleteById(disenoPId);
        return "El diseñoP " + disenoPRef.get().getIdDisenoP() + " fue eliminado con exito.";
    }

    @Override
    public String actualizarDisenoPPorId(DisenoPModel disenoP) {
        this.disenoPRepository.save(disenoP);
        return "El diseñoP con id " + disenoP.getIdDisenoP() + " fue actualizado con exito.";
    }

    @Override
    public Optional<DisenoPModel> mostrarDisenoCompartidoPorIdOrden(int idOrdenProd) {
        return this.disenoPRepository.listarDisenoCompartidoPorIdOrden(idOrdenProd);
    }

    @Override
    public void cambiarEstadoDisenoPorIdOrdenProd(int idOrdenProd, String estado) {
        this.disenoPRepository.actualizarEstadoDisenoPorIdOrdenProd(idOrdenProd, estado);
    }
}
