package com.example.Apiweb.service;

import com.example.Apiweb.model.EnvioModel;
import com.example.Apiweb.repository.IEnvioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class EnvioServiceImp implements IEnvioService{
    @Autowired
    IEnvioRepository envioRepository;

    @Override
    public String crearEnvio(EnvioModel envio) {
        this.envioRepository.save(envio);
        return "El envio " + envio.getIdEnvio() + " fue creado exitosamente";
    }

    @Override
    public List<EnvioModel> listarEnvios() {
        return this.envioRepository.findAll();
    }

    @Override
    public Optional<EnvioModel> obtenerEnvioPorId(int envioId) {
        return this.envioRepository.findById(envioId);
    }

    @Override
    public String eliminarEnvioPorId(int envioId) {
        Optional<EnvioModel> envioRef = this.envioRepository.findById(envioId);
        this.envioRepository.deleteById(envioId);
        return "El envio " + envioRef.get().getIdEnvio() + " fue eliminado con exito.";
    }

    @Override
    public String actualizarEnvioPorId(EnvioModel envio) {
        this.envioRepository.save(envio);
        return "El envio con id " + envio.getIdEnvio() + " fue actualizado con exito.";
    }
}
