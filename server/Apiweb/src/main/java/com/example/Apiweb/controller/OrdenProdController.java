package com.example.Apiweb.controller;

import com.example.Apiweb.exception.RecursoNoEncontradoException;
import com.example.Apiweb.model.OrdenProdModel;
import com.example.Apiweb.model.enums.Estado;
import com.example.Apiweb.service.IOrdenProdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Apiweb/v1/ordenProd")
@CrossOrigin
public class OrdenProdController {
    @Autowired
    private IOrdenProdService ordenProdService;

    @PostMapping("/")
    public ResponseEntity<String> crearOrdenProd(@RequestBody OrdenProdModel ordenProd) {
        ordenProdService.crearOrdenProd(ordenProd);
        return new ResponseEntity<String>(ordenProdService.crearOrdenProd(ordenProd), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<OrdenProdModel>> listarOrdenProds(){
        List<OrdenProdModel> ordenProd = ordenProdService.listarOrdenProds();
        return new ResponseEntity<>(ordenProd, HttpStatus.OK);
    }

    //consultar una ciudDepto por Id
    @GetMapping("/{ordenProdId}")
    public ResponseEntity<OrdenProdModel> buscarOrdenProd(@PathVariable Integer ordenProdId) {
        OrdenProdModel ordenProd = this.ordenProdService.obtenerOrdenProdPorId(ordenProdId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró la ordenProd con el id " + ordenProdId));
        return ResponseEntity.ok(ordenProd);
    }

    @GetMapping("/visualizarProductos/{idOrden}")
    public ResponseEntity<List<OrdenProdModel>> visualizarProductos(@PathVariable Integer idOrden){
        List<OrdenProdModel> productos = ordenProdService.mostrarProductosPorIdOrden(idOrden);
        return new ResponseEntity<List<OrdenProdModel>>(productos, HttpStatus.OK);
    }

    @PutMapping("/asignarDisenador/{idOrdenProd}/{disenadorAsignado}")
    public ResponseEntity<Integer> asignarDisenador(@PathVariable Integer idOrdenProd, @PathVariable Integer disenadorAsignado) {
        return new ResponseEntity<Integer>(ordenProdService.asignarDisenador(idOrdenProd, disenadorAsignado), HttpStatus.OK);
    }

}
