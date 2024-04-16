package com.example.Apiweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Apiweb.exception.CamposInvalidosException;
import com.example.Apiweb.exception.RecursoNoEncontradoException;
import com.example.Apiweb.model.DepartamentoModel;
import com.example.Apiweb.service.IDepartamentoService;

@RestController
@RequestMapping("/Apiweb/v1/departamento")
@CrossOrigin
public class DepartamentoController {
    @Autowired
    private IDepartamentoService departamentoService;

    @PostMapping("/")
    public ResponseEntity<String> crearDepartamento(@RequestBody DepartamentoModel departamento) {
        departamentoService.crearDepartamento(departamento);
        return new ResponseEntity<String>(departamentoService.crearDepartamento(departamento), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<DepartamentoModel>> listarDepartamentos(){
        List<DepartamentoModel> departamentos = departamentoService.listarDepartamentos();
        return new ResponseEntity<>(departamentos, HttpStatus.OK);
    }

    //consultar un departamento por Id
    @GetMapping("/{departamentoId}")
    public ResponseEntity<DepartamentoModel> buscarDepartamentoPorId(@PathVariable Integer departamentoId) {
        DepartamentoModel departamento = this.departamentoService.obtenerDepartamentoPorId(departamentoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró la categoria con el id " + departamentoId));
        return ResponseEntity.ok(departamento);
    }

    //actualizar la información básica de la categoria
    @PutMapping ("/{departamentoId}")
    public ResponseEntity<String> actualizarDepartamentoPorId(@PathVariable Integer departamentoId, @RequestBody DepartamentoModel detallesDepartamento) {
        DepartamentoModel departamento = this.departamentoService.obtenerDepartamentoPorId(departamentoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró la categoria con el id " + departamentoId));
        //obtenemos los datos que se van actualizar de la categoria y que son enviados del json
        String nombreActualizar = detallesDepartamento.getNombre();

        //Verificamos que estos campos actualizar no sean nulos o vacios y controlamos la excepcion
        if (nombreActualizar !=null && !nombreActualizar.isEmpty()){
            //asignamos los valores que vamos actualizar de el ingrediente
            departamento.setNombre(nombreActualizar);
            //guardamos los cambios
            return new ResponseEntity<String>(departamentoService.actualizarDepartamentoPorId(departamento),HttpStatus.OK);
        }
        else{
            throw new CamposInvalidosException("Error! El nombre y la descripcion de el departamento no puede estar vacio");
        }
    }
}
