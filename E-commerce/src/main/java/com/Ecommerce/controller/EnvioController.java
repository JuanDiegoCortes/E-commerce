package com.Ecommerce.controller;

import java.util.List;
import java.util.Optional;

import com.Ecommerce.model.EnvioModel;
import com.Ecommerce.service.IEnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
}


