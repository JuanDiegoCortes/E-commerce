package com.example.Apiweb.controller;

import com.example.Apiweb.exception.CamposInvalidosException;
import com.example.Apiweb.exception.RecursoNoEncontradoException;
import com.example.Apiweb.model.RolModel;
import com.example.Apiweb.service.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Apiweb/v1/rol")
@CrossOrigin
public class RolController {
    @Autowired
    private IRolService rolService;

    @PostMapping("/")
    public ResponseEntity<String> crearRol(@RequestBody RolModel rol) {
        rolService.crearRol(rol);
        return new ResponseEntity<String>(rolService.crearRol(rol), HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<RolModel>> listarRoles(){
        List<RolModel> roles = rolService.listarRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    //consultar un rol por Id
    @GetMapping("/{rolId}")
    public ResponseEntity<RolModel> buscarRolPorId(@PathVariable Integer rolId) {
        RolModel rol = this.rolService.obtenerRolPorId(rolId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró el rol con el id " + rolId));
        return ResponseEntity.ok(rol);
    }

    //actualizar la información básica del envío
    @PutMapping ("/{rolId}")
    public ResponseEntity<String> actualizarRolPorId(@PathVariable Integer rolId, @RequestBody RolModel detallesRol) {
        RolModel rol = this.rolService.obtenerRolPorId(rolId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró el rol con el id " + rolId));
        //obtenemos los datos que se van actualizar del rol y que son enviados del json
        String nombreActualizar = detallesRol.getNombre();

        //Verificamos que estos campos a actualizar no sean nulos o vacios y controlamos la excepcion
        if (nombreActualizar !=null && !nombreActualizar.isEmpty()){
            //asignamos los valores que vamos actualizar del rol
            rol.setNombre(nombreActualizar);

            //guardamos los cambios
            return new ResponseEntity<String>(rolService.actualizarRolPorId(rol),HttpStatus.OK);
        }
        else{
            throw new CamposInvalidosException("Error! El nombre no puede estar vacios");
        }
    }
}
