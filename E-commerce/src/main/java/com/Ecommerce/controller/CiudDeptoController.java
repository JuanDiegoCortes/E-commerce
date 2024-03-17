package com.Ecommerce.controller;


import com.Ecommerce.exception.RecursoNoEncontradoException;
import com.Ecommerce.model.CiudDeptoModel;
import com.Ecommerce.model.CiudadModel;
import com.Ecommerce.model.DepartamentoModel;
import com.Ecommerce.service.ICiudDeptoService;
import com.Ecommerce.service.ICiudadService;
import com.Ecommerce.service.IDepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Ecommerce/v1/ciudDepto")
public class CiudDeptoController {
    @Autowired
    private ICiudDeptoService ciudDeptoService;
    @Autowired
    private ICiudadService ciudadService;
    @Autowired
    private IDepartamentoService departamentoService;

    @PostMapping("/")
    public ResponseEntity<String> crearCiudDepto(@RequestBody CiudDeptoModel ciudDepto) {
        ciudDeptoService.crearCiudDepto(ciudDepto);
        return new ResponseEntity<String>(ciudDeptoService.crearCiudDepto(ciudDepto), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CiudDeptoModel>> listarCiudDepto(){
        List<CiudDeptoModel> ciudDepto = ciudDeptoService.listarCiudDepto();
        return new ResponseEntity<>(ciudDepto, HttpStatus.OK);
    }

    //consultar una ciudDepto por Id
    @GetMapping("/{ciudDeptoId}")
    public ResponseEntity<CiudDeptoModel> buscarCiudDeptoPorId(@PathVariable Integer ciudDeptoId) {
        CiudDeptoModel ciudDepto = this.ciudDeptoService.obtenerCiudDeptoPorId(ciudDeptoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró la ciudDepto con el id " + ciudDeptoId));
        return ResponseEntity.ok(ciudDepto);
    }
}
