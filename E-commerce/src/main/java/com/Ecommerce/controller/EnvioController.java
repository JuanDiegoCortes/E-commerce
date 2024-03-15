package com.Ecommerce.controller;

import java.util.List;
import java.util.Optional;

import com.Ecommerce.exception.CamposInvalidosException;
import com.Ecommerce.exception.RecursoNoEncontradoException;
import com.Ecommerce.model.CategoriaModel;
import com.Ecommerce.model.EnvioModel;
import com.Ecommerce.service.IEnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Ecommerce/v1/envio")
public class EnvioController {
    @Autowired
    private IEnvioService envioService;

    @PostMapping("/")
    public ResponseEntity<String> crearEnvio(@RequestBody EnvioModel envio) {
        //Verificar si el envio ya existe
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
        String nombreAtualizar2 = detallesEnvio.getTelefono();
        String nombreAtualizar3= detallesEnvio.getModalidadEntrega();


        //Verificamos que estos campos a actualizar no sean nulos o vacios y controlamos la excepcion
        if (nombreActualizar !=null && !nombreActualizar.isEmpty() && nombreAtualizar2 != null && !nombreAtualizar2.isEmpty()){
            //asignamos los valores que vamos actualizar del envio
            envio.setDireccion(nombreActualizar);
            envio.setTelefono(nombreAtualizar2);
            envio.setModalidadEntrega(nombreAtualizar3);
            //guardamos los cambios
            return new ResponseEntity<String>(envioService.actualizarEnvioPorId(envio),HttpStatus.OK);
        }
        else{
            throw new CamposInvalidosException("Error! La dirección, el teléfono y la modalidad de entrega del envío no pueden estar vacios");
        }
    }
}


