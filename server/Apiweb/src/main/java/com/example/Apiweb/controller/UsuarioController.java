package com.example.Apiweb.controller;


import com.example.Apiweb.exception.CamposInvalidosException;
import com.example.Apiweb.exception.RecursoNoEncontradoException;
import com.example.Apiweb.model.OrdenModel;
import com.example.Apiweb.model.RolUsuarioModel;
import com.example.Apiweb.model.UsuarioModel;
import com.example.Apiweb.service.IRolService;
import com.example.Apiweb.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Apiweb/v1/usuario")
@CrossOrigin
public class UsuarioController {
    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping("/")
    public ResponseEntity<String> crearUsuario(@RequestBody UsuarioModel usuario) {
        Optional<UsuarioModel> verificacion = usuarioService.obtenerUsuarioPorId(usuario.getCedula());
        if (verificacion.isPresent()){
            String mensaje = "Este usuario ya existe.";
            return new ResponseEntity<String>(mensaje, HttpStatus.BAD_REQUEST);
        }
        usuarioService.crearUsuario(usuario);
        return new ResponseEntity<String>(usuarioService.crearUsuario(usuario), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UsuarioModel>> listarUusuarios(){
        List<UsuarioModel> usuarios = usuarioService.listarUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    //consultar una categorias por Id
    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioModel> buscarUsuarioPorId(@PathVariable Integer usuarioId) {
        UsuarioModel usuario = this.usuarioService.obtenerUsuarioPorId(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró el usuario con el id " + usuarioId));
        return ResponseEntity.ok(usuario);
    }

    //actualizar la información básica de el usuario
    @PutMapping("/{usuarioId}")
    public ResponseEntity<String> actualizarUsuarioPorId(@PathVariable Integer usuarioId, @RequestBody UsuarioModel detallesUsuario) {
        UsuarioModel usuario = this.usuarioService.obtenerUsuarioPorId(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró el usuario con el id " + usuarioId));
        //obtenemos los datos que se van actualizar de el usuario y que son enviados del json
        Integer nombreActualizar = detallesUsuario.getCedula();
        String nombreActualizar2 = detallesUsuario.getNombre();
        String nombreActualizar3 = detallesUsuario.getCorreo();

        //Verificamos que estos campos actualizar no sean nulos o vacios y controlamos la excepcion
        if (nombreActualizar !=null && nombreActualizar2 != null && !nombreActualizar2.isEmpty() && nombreActualizar3 != null && !nombreActualizar3.isEmpty()){
            //asignamos los valores que vamos actualizar de el usuario
            usuario.setCedula(nombreActualizar);
            usuario.setNombre(nombreActualizar2);
            usuario.setCorreo(nombreActualizar3);
            //guardamos los cambios
            return new ResponseEntity<String>(usuarioService.actualizarUsuarioPorId(usuario),HttpStatus.OK);
        }
        else{
            throw new CamposInvalidosException("Error! Los datos del uisuario no pueden estar vacios.");
        }
    }
}
