package com.Ecommerce.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.Ecommerce.domain.CiudadDTO;
import com.Ecommerce.model.*;
import com.Ecommerce.service.ICiudDeptoService;
import com.Ecommerce.service.IDepartamentoService;
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
import com.Ecommerce.service.ICiudadService;

@Controller
@RequestMapping("/Ecommerce/v1/ciudad")
public class CiudadController {
    @Autowired
    private ICiudadService ciudadService;
    @Autowired
    private IDepartamentoService departamentoService;
    @Autowired
    private ICiudDeptoService ciudDeptoService;

    @PostMapping("/")
    public ResponseEntity<String> crearCiudad(@RequestBody CiudadDTO ciudadDTO) {
        //Verificar si la categoria ya existe
        boolean bandera = true;
        Optional<CiudadModel> verificacion = ciudadService.obtenerCiudadPorId(ciudadDTO.getIdCiudad());
        if (verificacion.isPresent()) {
            String mensaje = "Esta ciudad ya existe.";
            return new ResponseEntity<String>(mensaje, HttpStatus.BAD_REQUEST);
        }

        //Crear instancia de un ciudadModel
        CiudadModel ciudad = new CiudadModel();
        ciudad.setNombre(ciudadDTO.getNombre());

        //Capturar el departamento y verificar que existan
        if (ciudadDTO.getIdDepartamento() != null) {
            Map<String, Integer> departamentoLiF = ciudadDTO.getIdDepartamento();
            Integer idDepartamento = departamentoLiF.get("idDepartamento");
            DepartamentoModel departamento = this.departamentoService.obtenerDepartamentoPorId(idDepartamento)
                    .orElseThrow(() -> new RecursoNoEncontradoException("No esta el departamento con id: " + idDepartamento));
            if (departamento == null) {
                bandera = false;
                throw new RecursoNoEncontradoException("No se encontró el departamento con ID: " + idDepartamento);
            }

            if (bandera) {
                System.out.println("Bandera: "+ bandera);
                ciudadService.crearCiudad(ciudad);

                CiudDeptoModel ciudDepto = new CiudDeptoModel();
                DepartamentoModel departamentoR = departamentoService.obtenerDepartamentoPorId(idDepartamento).get();
                ciudDepto.setIdCiudad(ciudad);
                ciudDepto.setIdDepartamento(departamentoR);
                this.ciudDeptoService.crearCiudDepto(ciudDepto);
                return new ResponseEntity<String>(ciudadService.crearCiudad(ciudad), HttpStatus.OK);
            } else{
                String mensaje = "Verifica que los datos ingresados sean correctos.";
                return new ResponseEntity<>(mensaje, HttpStatus.BAD_REQUEST); //Envia esto cuando no existe el departamento
            }
        }else{
            String mensaje = "Verifica que hayas ingresado el departamento de la ciudad.";
            return new ResponseEntity<>(mensaje, HttpStatus.BAD_REQUEST);
        }
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
