package com.example.Apiweb.controller;

import com.example.Apiweb.exception.RecursoNoEncontradoException;
import com.example.Apiweb.model.ProdTallaModel;
import com.example.Apiweb.service.IProdTallaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Ecommerce/v1/prodTalla")
@CrossOrigin
public class ProdTallaController {
    @Autowired
    private IProdTallaService prodTallaService;

    @PostMapping("/")
    public ResponseEntity<String> crearProdTalla(@RequestBody ProdTallaModel prodTalla) {
        prodTallaService.crearProdTalla(prodTalla);
        return new ResponseEntity<String>(prodTallaService.crearProdTalla(prodTalla), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProdTallaModel>> listarProdTallas(){
        List<ProdTallaModel> prodTallas = prodTallaService.listarProdTallas();
        return new ResponseEntity<>(prodTallas, HttpStatus.OK);
    }

    //consultar una orden por Id
    @GetMapping("/{prodTallaId}")
    public ResponseEntity<ProdTallaModel> obtenerProdTallaPorId(@PathVariable Integer prodTallaId) {
        ProdTallaModel prodTalla = this.prodTallaService.obtenerProdTallaPorId(prodTallaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró la prodTalla con el id " + prodTallaId));
        return ResponseEntity.ok(prodTalla);
    }
}
