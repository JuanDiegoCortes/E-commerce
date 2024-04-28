package com.example.Apiweb.controller;

import com.example.Apiweb.exception.RecursoNoEncontradoException;
import com.example.Apiweb.model.RolUsuarioModel;
import com.example.Apiweb.service.IRolUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Apiweb/v1/rolUsuario")
@CrossOrigin
public class RolUsuarioController {
    @Autowired
    private IRolUsuarioService rolUsuarioService;

    @PostMapping("/")
    public ResponseEntity<String> crearRolUsuario(@RequestBody RolUsuarioModel rolUsuario) {
        rolUsuarioService.crearRolUsuario(rolUsuario);
        return new ResponseEntity<String>(rolUsuarioService.crearRolUsuario(rolUsuario), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<RolUsuarioModel>> listarRolesUsuario(){
        List<RolUsuarioModel> rolesUsuario = rolUsuarioService.listarRolesUsuario();
        return new ResponseEntity<>(rolesUsuario, HttpStatus.OK);
    }

    //consultar una orden por Id
    @GetMapping("/{rolUsuarioId}")
    public ResponseEntity<RolUsuarioModel> obtenerRolUsuarioPorId(@PathVariable Integer rolUsuarioId) {
        RolUsuarioModel rolUsuario = this.rolUsuarioService.obtenerRolUsuarioPorId(rolUsuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró el rolUsuario con el id " + rolUsuarioId));
        return ResponseEntity.ok(rolUsuario);
    }

    @GetMapping("/autenticacionUsuario/{cedula}/{contrasena}")
    public ResponseEntity<RolUsuarioModel> autenticacionUsuario(@PathVariable Integer cedula, @PathVariable String contrasena) {
        RolUsuarioModel usuario = this.rolUsuarioService.verUsuarioPorCedulaYContrasena(cedula,contrasena)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró el usuario con la cedula: " + cedula + " y contraseña: " + contrasena));
        return ResponseEntity.ok(usuario);
    }
}
