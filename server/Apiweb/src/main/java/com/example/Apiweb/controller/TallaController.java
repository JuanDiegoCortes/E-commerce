package com.example.Apiweb.controller;

import com.example.Apiweb.exception.CamposInvalidosException;
import com.example.Apiweb.exception.RecursoNoEncontradoException;
import com.example.Apiweb.model.TallaModel;
import com.example.Apiweb.service.ITallaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Apiweb/v1/talla")
@CrossOrigin
public class TallaController {
    @Autowired
    private ITallaService tallaService;

    @PostMapping("/")
    public ResponseEntity<String> crearTalla(@RequestBody TallaModel talla) {
        //Verificar si la talla ya existe
        Optional<TallaModel> verificacion = tallaService.obtenerTallaPorId(talla.getIdTalla());
        if (verificacion.isPresent()){
            String mensaje = "Esta talla ya existe.";
            return new ResponseEntity<String>(mensaje, HttpStatus.BAD_REQUEST);
        }
        tallaService.crearTalla(talla);
        return new ResponseEntity<String>(tallaService.crearTalla(talla), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<TallaModel>> listarTallas(){
        List<TallaModel> tallas = tallaService.listarTallas();
        return new ResponseEntity<>(tallas, HttpStatus.OK);
    }

    //consultar una talla por Id
    @GetMapping("/{tallaId}")
    public ResponseEntity<TallaModel> buscarTallaPorId(@PathVariable Integer tallaId) {
        TallaModel talla = this.tallaService.obtenerTallaPorId(tallaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró la talla con el id " + tallaId));
        return ResponseEntity.ok(talla);
    }

    //actualizar la información básica de la categoria
    @PutMapping ("/{tallaId}")
    public ResponseEntity<String> actualizarTallaPorId(@PathVariable Integer tallaId, @RequestBody TallaModel detallesTalla) {
        TallaModel talla = this.tallaService.obtenerTallaPorId(tallaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró la talla con el id " + tallaId));
        //obtenemos los datos que se van actualizar de la talla y que son enviados del json
        String nombreActualizar = detallesTalla.getMedida();

        //Verificamos que estos campos actualizar no sean nulos o vacios y controlamos la excepcion
        if (nombreActualizar !=null && !nombreActualizar.isEmpty()){
            //asignamos los valores que vamos actualizar de la talla
            talla.setMedida(nombreActualizar);
            //guardamos los cambios
            return new ResponseEntity<String>(tallaService.actualizarTallaPorId(talla),HttpStatus.OK);
        }
        else{
            throw new CamposInvalidosException("Error! La medida de la talla no pueden estar vacia.");
        }
    }
}
