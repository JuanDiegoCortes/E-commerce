package com.example.Apiweb.controller;

import java.util.List;
import java.util.Optional;

import com.example.Apiweb.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Apiweb.exception.RecursoNoEncontradoException;
import com.example.Apiweb.service.ICategoriaService;

@RestController
@RequestMapping("/Apiweb/v1/categoria")
@CrossOrigin
public class CategoriaController {
    @Autowired
    private ICategoriaService categoriaService;

    @PostMapping("/")
    public ResponseEntity<String> crearCategoria(@RequestBody CategoriaModel categoria) {

        Optional<CategoriaModel> verificacion = categoriaService.obtenerCategoriaPorId(categoria.getIdCategoria());
        if (verificacion.isPresent()){
            String mensaje = "Esta categoria ya existe.";
            return new ResponseEntity<String>(mensaje, HttpStatus.BAD_REQUEST);
        }
        System.out.println(categoria.getSubIdCategoria().getIdCategoria());
        CategoriaModel subCategoria = categoriaService.obtenerCategoriaPorId(categoria.getSubIdCategoria().getIdCategoria())
                .orElseThrow(()-> new RecursoNoEncontradoException("La SubCategoria no existe."));

        //Crear instancia de un categoriaModel
        CategoriaModel categoriaN = new CategoriaModel();
        categoriaN.setIdCategoria(categoria.getIdCategoria());
        categoriaN.setDescripcion(categoria.getDescripcion());
        categoriaN.setNombre(categoria.getNombre());
        categoriaN.setSubIdCategoria(subCategoria);
        categoriaService.crearCategoria(categoriaN);

        return new ResponseEntity<String>("Categoria creada con exito.", HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoriaModel>> listarCategorias(){
        List<CategoriaModel> categorias = categoriaService.listarCategorias();
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    //consultar una categorias por Id
    @GetMapping("/{categoriaId}")
    public ResponseEntity<CategoriaModel> buscarCategoriaPorId(@PathVariable Integer categoriaId) {
        CategoriaModel categoria = this.categoriaService.obtenerCategoriaPorId(categoriaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró la categoria con el id " + categoriaId));
        return ResponseEntity.ok(categoria);
    }

    //actualizar la información básica de la categoria
    @PutMapping ("/{categoriaId}")
    public ResponseEntity<String> actualizarCategoriaPorId(@PathVariable Integer categoriaId, @RequestBody CategoriaModel detallesCategoria) {
        CategoriaModel categoria = this.categoriaService.obtenerCategoriaPorId(categoriaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró la categoria con el id " + categoriaId));
        //obtenemos los datos que se van actualizar de la categoria y que son enviados del json
        String nombreActualizar = detallesCategoria.getNombre();
        String nombreActualizar2 = detallesCategoria.getDescripcion();

        if (nombreActualizar != null && !nombreActualizar.isEmpty() && nombreActualizar != "") {
            categoria.setNombre(nombreActualizar);
        }
        if (nombreActualizar2 != null && !nombreActualizar2.isEmpty() && nombreActualizar2 != "") {
            categoria.setDescripcion(nombreActualizar2);
        }

        if (nombreActualizar == "" && nombreActualizar2 == "") {
            return new ResponseEntity<>("Error!. No hay datos para actualizar.", HttpStatus.BAD_REQUEST);
        } else {
            this.categoriaService.actualizarCategoriaPorId(categoria);
            return new ResponseEntity<>("Categoria actualizada con exito.", HttpStatus.OK);
        }
    }
}
