package com.example.Apiweb.controller;

import java.util.List;
import java.util.Optional;

import com.example.Apiweb.exception.CamposInvalidosException;
import com.example.Apiweb.exception.RecursoNoEncontradoException;
import com.example.Apiweb.model.EnvioModel;
import com.example.Apiweb.model.enums.ModalidadEntrega;
import com.example.Apiweb.service.ICiudadService;
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
    @Autowired
    private ICiudadService ciudadService;

    @PostMapping("/")
    public ResponseEntity<String> crearEnvio(@RequestBody EnvioModel envio) {
        //Verificar si el envio ya existe
        ciudadService.obtenerCiudadPorId(envio.getIdCiudad().getIdCiudad())
                .orElseThrow(()-> new RecursoNoEncontradoException("La ciudad no existe."));

        Optional<EnvioModel> verificacion = envioService.obtenerEnvioPorId(envio.getIdEnvio());
        if (verificacion.isPresent()){
            String mensaje = "Este envío ya existe.";
            return new ResponseEntity<String>(mensaje, HttpStatus.BAD_REQUEST);
        }
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
        String nombreActualizar = detallesEnvio.getDireccion();
        String nombreActualizar2 = detallesEnvio.getTelefono();
        ModalidadEntrega nombreActualizar3 = detallesEnvio.getModalidadEntrega();


        //Verificamos que estos campos a actualizar no sean nulos o vacios y controlamos la excepcion
        if (nombreActualizar !=null && !nombreActualizar.isEmpty() && nombreActualizar2 != null && !nombreActualizar2.isEmpty() && nombreActualizar3 != null){
            //asignamos los valores que vamos actualizar del envio
            envio.setDireccion(nombreActualizar);
            envio.setTelefono(nombreActualizar2);
            envio.setModalidadEntrega(nombreActualizar3);
            //guardamos los cambios
            return new ResponseEntity<String>(envioService.actualizarEnvioPorId(envio),HttpStatus.OK);
        }
        else{
            throw new CamposInvalidosException("Error! La dirección, el teléfono y la modalidad del envío no pueden estar vacios");
        }
    }
}


