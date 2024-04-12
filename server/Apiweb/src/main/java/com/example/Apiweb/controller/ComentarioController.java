package com.example.Apiweb.controller;

import java.util.List;
import java.util.Optional;

import com.example.Apiweb.model.*;
import com.example.Apiweb.service.IDisenoPService;
import com.example.Apiweb.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Apiweb.exception.CamposInvalidosException;
import com.example.Apiweb.exception.RecursoNoEncontradoException;
import com.example.Apiweb.service.IComentarioService;

@RestController
@RequestMapping("/Apiweb/v1/comentario")
@CrossOrigin
public class ComentarioController {
    @Autowired
    private IComentarioService comentarioService;
    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private IDisenoPService disenoPService;

    @PostMapping("/")
    public ResponseEntity<String> crearComentario(@RequestBody ComentarioModel comentario) {
        comentarioService.crearComentario(comentario);
        return new ResponseEntity<String>(comentarioService.crearComentario(comentario), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<ComentarioModel>> listarComentarios(){
        List<ComentarioModel> comentarios = comentarioService.listarComentarios();
        return new ResponseEntity<>(comentarios, HttpStatus.OK);
    }

    //consultar un comentario por Id
    @GetMapping("/{comentarioId}")
    public ResponseEntity<ComentarioModel> buscarComentarioPorId(@PathVariable Integer comentarioId) {
        ComentarioModel comentario = this.comentarioService.obtenerComentarioPorId(comentarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró la comentario con el id " + comentarioId));
        return ResponseEntity.ok(comentario);
    }

    //actualizar la información básica de la comentario
    @PutMapping ("/{comentarioId}")
    public ResponseEntity<String> actualizarComentarioPorId(@PathVariable Integer comentarioId, @RequestBody ComentarioModel detallesComentario) {
        ComentarioModel comentario = this.comentarioService.obtenerComentarioPorId(comentarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró el comentario con el id " + comentarioId));
        //obtenemos los datos que se van actualizar de la categoria y que son enviados del json
        String nombreActualizar = detallesComentario.getTexto();

        //Verificamos que estos campos actualizar no sean nulos o vacios y controlamos la excepcion
        if (nombreActualizar !=null && !nombreActualizar.isEmpty()){
            //asignamos los valores que vamos actualizar de el ingrediente
            comentario.setTexto(nombreActualizar);
            //guardamos los cambios
            return new ResponseEntity<String>(comentarioService.actualizarComentarioPorId(comentario),HttpStatus.OK);
        }
        else{
            throw new CamposInvalidosException("Error! El texto del comentario no puede esta vacio.");
        }
    }

    @GetMapping("/visualizarComentarios/{idDisenoP}")
    public ResponseEntity<List<ComentarioModel>> visualizarComentarios(@PathVariable Integer idDisenoP){
        List<ComentarioModel> comentarios = comentarioService.mostrarComentariosPorIdDisenoP(idDisenoP);
        return new ResponseEntity<List<ComentarioModel>>(comentarios, HttpStatus.OK);
    }
}
