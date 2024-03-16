package com.Ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Ecommerce.exception.CamposInvalidosException;
import com.Ecommerce.exception.RecursoNoEncontradoException;
import com.Ecommerce.model.CategoriaModel;
import com.Ecommerce.service.ICategoriaService;

@Controller
@RequestMapping("/Ecommerce/v1/categoria")
public class CategoriaController {
    @Autowired
    private ICategoriaService categoriaService;

    @PostMapping("/")
    public ResponseEntity<String> crearCategoria(@RequestBody CategoriaModel categoria) {
        //Verificar si la categoria ya existe
        Optional<CategoriaModel> verificacion = categoriaService.obtenerCategoriaPorId(categoria.getIdCategoria());
        if (verificacion.isPresent()){
            String mensaje = "Esta categoria ya existe.";
            return new ResponseEntity<String>(mensaje, HttpStatus.BAD_REQUEST);
        }
        categoriaService.crearCategoria(categoria);
        return new ResponseEntity<String>(categoriaService.crearCategoria(categoria), HttpStatus.OK);
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
