package com.Ecommerce.controller;

import com.Ecommerce.exception.RecursoNoEncontradoException;
import com.Ecommerce.model.ProdTallaModel;
import com.Ecommerce.model.ProductoModel;
import com.Ecommerce.model.TallaModel;
import com.Ecommerce.service.IProdTallaService;
import com.Ecommerce.service.IProductoService;
import com.Ecommerce.service.ITallaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Ecommerce/v1/prodTalla")
public class ProdTallaController {
    @Autowired
    private IProdTallaService prodTallaService;
    @Autowired
    private IProductoService productoService;
    @Autowired
    private ITallaService tallaService;

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
