package com.Ecommerce.controller;

import com.Ecommerce.exception.RecursoNoEncontradoException;
import com.Ecommerce.model.OrdenModel;
import com.Ecommerce.model.OrdenProdModel;
import com.Ecommerce.model.ProductoModel;
import com.Ecommerce.service.IOrdenProdService;
import com.Ecommerce.service.IOrdenService;
import com.Ecommerce.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Ecommerce/v1/ordenProd")
public class OrdenProdController {
    @Autowired
    private IOrdenProdService ordenProdService;
    @Autowired
    private IOrdenService ordenService;
    @Autowired
    private IProductoService productoService;

    @PostMapping("/")
    public ResponseEntity<String> crearOrdenProd(@RequestBody OrdenProdModel ordenProd) {
        ordenProdService.crearOrdenProd(ordenProd);
        return new ResponseEntity<String>(ordenProdService.crearOrdenProd(ordenProd), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<OrdenProdModel>> listarCiudDepto(){
        List<OrdenProdModel> ordenProd = ordenProdService.listarOrdenProds();
        return new ResponseEntity<>(ordenProd, HttpStatus.OK);
    }

    //consultar una ciudDepto por Id
    @GetMapping("/{ordenProdId}")
    public ResponseEntity<OrdenProdModel> buscarOrdenProd(@PathVariable Integer ordenPrdoId) {
        OrdenProdModel ordenProd = this.ordenProdService.obtenerOrdenProdPorId(ordenPrdoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró la ordenProd con el id " + ordenPrdoId));
        return ResponseEntity.ok(ordenProd);
    }
}
