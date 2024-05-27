package com.example.Apiweb.controller;

import com.example.Apiweb.exception.RecursoNoEncontradoException;
import com.example.Apiweb.model.CategoriaModel;
import com.example.Apiweb.model.ProdTallaModel;
import com.example.Apiweb.model.ProductoModel;
import com.example.Apiweb.service.ICategoriaService;
import com.example.Apiweb.service.IProdTallaService;
import com.example.Apiweb.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Apiweb/v1/prodTalla")
@CrossOrigin
public class ProdTallaController {
    @Autowired
    private IProdTallaService prodTallaService;
    @Autowired
    private IProductoService productoService;
    @Autowired
    private ICategoriaService categoriaService;

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

    @GetMapping("/prodTallaPorIdProducto/{idProducto}")
    public ResponseEntity<List<ProdTallaModel>> obtenerProdTallaPorIdProducto(@PathVariable Integer idProducto) {
        List<ProdTallaModel> prodTalla = this.prodTallaService.obtenerProdTallaPorIdProducto(idProducto);
        return ResponseEntity.ok(prodTalla);
    }

    @PutMapping("/{prodTallaId}")
    public ResponseEntity<String> actualizarProdTallaPorId(@PathVariable Integer prodTallaId, @RequestBody ProdTallaModel prodTalla) {
        ProdTallaModel prodTallaRef = this.prodTallaService.obtenerProdTallaPorId(prodTallaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró la prodTalla con el id " + prodTallaId));
        System.out.println(prodTalla.getIdProducto().getIdProducto());
        ProductoModel producto = this.productoService.obtenerProductoPorId(prodTalla.getIdProducto().getIdProducto())
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró el producto con el id " + prodTalla.getIdProducto().getIdProducto()));

        if (prodTalla.getCantidad() != null) {
            prodTallaRef.setCantidad(prodTalla.getCantidad());
        }
        if (prodTalla.getIdProducto().getDescripcion() != null && prodTalla.getIdProducto().getDescripcion() != "") {
            producto.setDescripcion(prodTalla.getIdProducto().getDescripcion());
        }
        if (prodTalla.getIdProducto().getNombre() != null && prodTalla.getIdProducto().getNombre() != "") {
            producto.setNombre(prodTalla.getIdProducto().getNombre());
        }
        if (prodTalla.getIdProducto().getPrecio() != null) {
            producto.setPrecio(prodTalla.getIdProducto().getPrecio());
        }
        if (prodTalla.getIdProducto().getEstado() != null) {
            producto.setEstado(prodTalla.getIdProducto().getEstado());
        }
        if (prodTalla.getIdProducto().getPersonalizable() != null) {
            producto.setPersonalizable(prodTalla.getIdProducto().getPersonalizable());
        }
        if (prodTalla.getIdProducto().getIdCategoria() != null) {
            CategoriaModel categoria = this.categoriaService.obtenerCategoriaPorId(producto.getIdCategoria().getIdCategoria())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró la categoria con el id " + producto.getIdCategoria().getIdCategoria()));
            producto.setIdCategoria(categoria);
        }

        if (prodTalla.getCantidad() == null
                && prodTalla.getIdProducto().getDescripcion() == "" && prodTalla.getIdProducto().getNombre() == ""
                && prodTalla.getIdProducto().getEstado() == null && prodTalla.getIdProducto().getPersonalizable() == null
                && prodTalla.getIdProducto().getPrecio() == null && prodTalla.getIdProducto().getIdCategoria() == null) {
            return new ResponseEntity<>("Error!. No hay datos para actualizar.", HttpStatus.BAD_REQUEST);
        } else {
            this.productoService.actualizarProductoPorId(producto);
            this.prodTallaService.actualizarProdTallaPorId(prodTallaRef);
            return new ResponseEntity<>("Producto actualizada con exito.", HttpStatus.OK);
        }
    }
}
