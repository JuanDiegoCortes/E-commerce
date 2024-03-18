package com.example.Apiweb.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.Apiweb.domain.CategoriaDTO;
import com.example.Apiweb.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Apiweb.exception.CamposInvalidosException;
import com.example.Apiweb.exception.RecursoNoEncontradoException;
import com.example.Apiweb.service.ICategoriaService;

@RestController
@RequestMapping("/Apiweb/v1/categoria")
@CrossOrigin
public class CategoriaController {
    @Autowired
    private ICategoriaService categoriaService;

    @PostMapping("/")
    public ResponseEntity<String> crearCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        //Verificar si la categoria ya existe
        boolean bandera = true;
        Optional<CategoriaModel> verificacion = categoriaService.obtenerCategoriaPorId(categoriaDTO.getIdCategoria());
        if (verificacion.isPresent()){
            String mensaje = "Esta categoria ya existe.";
            return new ResponseEntity<String>(mensaje, HttpStatus.BAD_REQUEST);
        }

        CategoriaModel subCategoria = categoriaService.obtenerCategoriaPorId(categoriaDTO.getIdCategoria())
                .orElseThrow(()-> new RecursoNoEncontradoException("La categoria no existe."));

        //Crear instancia de un categoriaModel
        CategoriaModel categoria = new CategoriaModel();
        categoria.setDescripcion(categoriaDTO.getDescripcion());
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setSubIdCategoria(subCategoria);

        //Capturar lasa subCategorias y verificar que existan
        if (categoriaDTO.getIdCategoria() != null) {
            List<Map<String, Integer>> listarCategorias = categoriaDTO.getSubCategorias();
            for (Map<String, Integer> Categorias : listarCategorias) {
                Integer idCategoria = Categorias.get("idCategoria");
                CategoriaModel Categoria = this.categoriaService.obtenerCategoriaPorId(idCategoria)
                        .orElseThrow(() -> new RecursoNoEncontradoException("No esta la categoria con id: " + idCategoria));
                if (Categoria == null) {
                    bandera = false;
                    throw new RecursoNoEncontradoException("No se encontró la categoria con ID: " + idCategoria);
                }
            }
            if (bandera) {
                System.out.println("Bandera: "+ bandera);
                categoriaService.crearCategoria(categoria);
                return new ResponseEntity<String>(categoriaService.crearCategoria(categoria), HttpStatus.OK);
            } else{
                String mensaje = "Verifica que los datos ingresados sean correctos.";
                return new ResponseEntity<>(mensaje, HttpStatus.BAD_REQUEST); //Envia esto cuando no existen las categorias
            }
        }else{
            String mensaje = "Verifica que hayas ingresado las categorias de la categoria.";
            return new ResponseEntity<>(mensaje, HttpStatus.BAD_REQUEST);
        }
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

        //Verificamos que estos campos actualizar no sean nulos o vacios y controlamos la excepcion
        if (nombreActualizar !=null && !nombreActualizar.isEmpty() && nombreActualizar2 != null && !nombreActualizar2.isEmpty()){
            //asignamos los valores que vamos actualizar de el ingrediente
            categoria.setNombre(nombreActualizar);
            categoria.setDescripcion(nombreActualizar2);
            //guardamos los cambios
            return new ResponseEntity<String>(categoriaService.actualizarCategoriaPorId(categoria),HttpStatus.OK);
        }
        else{
            throw new CamposInvalidosException("Error! El nombre y la descripcion de la categoria no pueden estar vacios");
        }
    }
}
