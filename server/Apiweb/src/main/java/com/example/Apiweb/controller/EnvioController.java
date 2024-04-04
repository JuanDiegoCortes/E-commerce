package com.example.Apiweb.controller;

import java.util.List;

import com.example.Apiweb.exception.CamposInvalidosException;
import com.example.Apiweb.exception.RecursoNoEncontradoException;
import com.example.Apiweb.model.EnvioModel;
import com.example.Apiweb.model.enums.ModalidadEntrega;
import com.example.Apiweb.service.IEnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Apiweb/v1/envio")
@CrossOrigin
public class EnvioController {
    @Autowired
    private IEnvioService envioService;

    @PostMapping("/")
    public ResponseEntity<String> crearEnvio(@RequestBody EnvioModel envio) {
        envioService.crearEnvio(envio);
        return new ResponseEntity<String>(envioService.crearEnvio(envio), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<EnvioModel>> listarEnvios(){
        List<EnvioModel> envios = envioService.listarEnvios();
        return new ResponseEntity<>(envios, HttpStatus.OK);
    }

    //consultar un envio por Id
    @GetMapping("/{envioId}")
    public ResponseEntity<EnvioModel> obtenerEnvioPorId(@PathVariable Integer envioId) {
        EnvioModel envio = this.envioService.obtenerEnvioPorId(envioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró el envío con el id " + envioId));
        return ResponseEntity.ok(envio);
    }

    //actualizar la información básica del envío
    @PutMapping ("/{envioId}")
    public ResponseEntity<String> actualizarEnvioPorId(@PathVariable Integer envioId, @RequestBody EnvioModel detallesEnvio) {
        EnvioModel envio = this.envioService.obtenerEnvioPorId(envioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró el envío con el id " + envioId));
        //obtenemos los datos que se van actualizar del envío y que son enviados del json
        String nombreActualizar1 = detallesEnvio.getNombre();
        String nombreActualizar2 = detallesEnvio.getApellido();
        String nombreActualizar3 = detallesEnvio.getTelefono();
        String nombreActualizar4 = detallesEnvio.getDireccion();
        String nombreActualizar5 = detallesEnvio.getCodigoPostal();
        String nombreActualizar6 = detallesEnvio.getReferencias();
        ModalidadEntrega nombreActualizar7 = detallesEnvio.getModalidadEntrega();


        //Verificamos que estos campos a actualizar no sean nulos o vacios y controlamos la excepcion
        if (nombreActualizar1 !=null && !nombreActualizar1.isEmpty() && nombreActualizar2 != null && !nombreActualizar2.isEmpty()
                && nombreActualizar3 != null && nombreActualizar4 !=null && !nombreActualizar4.isEmpty() && nombreActualizar5 !=null
                && !nombreActualizar6.isEmpty() && nombreActualizar7 !=null){
            //asignamos los valores que vamos actualizar del envio
            envio.setNombre(nombreActualizar1);
            envio.setApellido(nombreActualizar2);
            envio.setTelefono(nombreActualizar3);
            envio.setDireccion(nombreActualizar4);
            envio.setCodigoPostal(nombreActualizar5);
            envio.setReferencias(nombreActualizar6);
            envio.setModalidadEntrega(nombreActualizar7);
            //guardamos los cambios
            return new ResponseEntity<String>(envioService.actualizarEnvioPorId(envio),HttpStatus.OK);
        }
        else{
            throw new CamposInvalidosException("Error! Las caracteristicas del envío no pueden estar vacias");
        }
    }
}


