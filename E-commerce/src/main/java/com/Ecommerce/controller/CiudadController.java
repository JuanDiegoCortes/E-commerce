package com.Ecommerce.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.Ecommerce.model.*;
import com.Ecommerce.service.ICiudDeptoService;
import com.Ecommerce.service.IDepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.Ecommerce.exception.CamposInvalidosException;
import com.Ecommerce.exception.RecursoNoEncontradoException;
import com.Ecommerce.service.ICiudadService;

@Controller
@RequestMapping("/Ecommerce/v1/ciudad")
@CrossOrigin
public class CiudadController {
    @Autowired
    private ICiudadService ciudadService;
    @Autowired
    private IDepartamentoService departamentoService;

    @PostMapping("/")
    public ResponseEntity<String> crearCiudad(@RequestBody CiudadModel ciudad) {
        //Verificar si la ciudad y el departamento ya existe
        DepartamentoModel departamento = departamentoService.obtenerDepartamentoPorId(ciudad.getIdDepartamento().getIdDepartamento())
                .orElseThrow(()-> new RecursoNoEncontradoException("El departamento no existe."));

        Optional<CiudadModel> verificacion = ciudadService.obtenerCiudadPorId(ciudad.getIdCiudad());
        if (verificacion.isPresent()) {
            String mensaje = "Esta ciudad ya existe.";
            return new ResponseEntity<String>(mensaje, HttpStatus.BAD_REQUEST);
        }
        ciudadService.crearCiudad(ciudad);
        return new ResponseEntity<String>(ciudadService.crearCiudad(ciudad), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CiudadModel>> listarCiudades(){
        List<CiudadModel> ciudades = ciudadService.listarCiudades();
        return new ResponseEntity<>(ciudades, HttpStatus.OK);
    }

    //consultar una categorias por Id
    @GetMapping("/{ciudadId}")
    public ResponseEntity<CiudadModel> buscarCiudadPorId(@PathVariable Integer ciudadId) {
        CiudadModel ciudad = this.ciudadService.obtenerCiudadPorId(ciudadId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró la categoria con el id " + ciudadId));
        return ResponseEntity.ok(ciudad);
    }

    //actualizar la información básica de la categoria
    @PutMapping ("/{ciudadId}")
    public ResponseEntity<String> actualizarCiudadPorId(@PathVariable Integer ciudadId, @RequestBody CiudadModel detallesCiudad) {
        CiudadModel ciudad = this.ciudadService.obtenerCiudadPorId(ciudadId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró la ciudad con el id " + ciudadId));
        //obtenemos los datos que se van actualizar de la categoria y que son enviados del json
        String nombreActualizar = detallesCiudad.getNombre();

        //Verificamos que estos campos actualizar no sean nulos o vacios y controlamos la excepcion
        if (nombreActualizar !=null && !nombreActualizar.isEmpty()){
            //asignamos los valores que vamos actualizar de el ingrediente
            ciudad.setNombre(nombreActualizar);
            //guardamos los cambios
            return new ResponseEntity<String>(ciudadService.actualizarCiudadPorId(ciudad),HttpStatus.OK);
        }
        else{
            throw new CamposInvalidosException("Error! El nombre de la ciudad no puede estar vacio");
        }
    }
}
