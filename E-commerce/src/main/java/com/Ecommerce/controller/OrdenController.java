package com.Ecommerce.controller;

import com.Ecommerce.model.EnvioModel;
import com.Ecommerce.model.OrdenModel;
import com.Ecommerce.service.IEnvioService;
import com.Ecommerce.service.IOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Ecommerce/v1/orden")
public class OrdenController {
    @Autowired
    private IOrdenService ordenService;

    @PostMapping("/")
    public ResponseEntity<String> crearOrden(@RequestBody OrdenModel orden) {
        //Verificar si la orden ya existe
        Optional<OrdenModel> verificacion = ordenService.obtenerOrdenPorId(orden.getIdOrden());
        if (verificacion.isPresent()){
            String mensaje = "Esta orden ya existe.";
            return new ResponseEntity<String>(mensaje, HttpStatus.BAD_REQUEST);
        }
        ordenService.crearOrden(orden);
        return new ResponseEntity<String>(ordenService.crearOrden(orden), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<OrdenModel>> listarOrdenes(){
        List<OrdenModel> ordenes = ordenService.listarOrdenes();
        return new ResponseEntity<>(ordenes, HttpStatus.OK);
    }

}
