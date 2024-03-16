package com.Ecommerce.controller;


import com.Ecommerce.exception.CamposInvalidosException;
import com.Ecommerce.exception.RecursoNoEncontradoException;
import com.Ecommerce.model.CategoriaModel;
import com.Ecommerce.model.UsuarioModel;
import com.Ecommerce.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Ecommerce/v1/usuario")
public class UsuarioController {
    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping("/")
    public ResponseEntity<String> crearUsuario(@RequestBody UsuarioModel usuario) {
        //Verificar si el usuario ya existe
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
    @GetMapping("/{usuariosId}")
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
        String nombreAtualizar3 = detallesUsuario.getCorreo();
        String nombreAtualizar4 = detallesUsuario.getContraseña();

        //Verificamos que estos campos actualizar no sean nulos o vacios y controlamos la excepcion
        if (nombreActualizar !=null && nombreActualizar2 != null && !nombreActualizar2.isEmpty() && nombreAtualizar3 != null && !nombreAtualizar3.isEmpty() && nombreAtualizar4 != null && !nombreAtualizar4.isEmpty()){
            //asignamos los valores que vamos actualizar de el usuario
            usuario.setCedula(nombreActualizar);
            usuario.setNombre(nombreActualizar2);
            usuario.setCorreo(nombreAtualizar3);
            usuario.setContraseña(nombreAtualizar4);
            //guardamos los cambios
            return new ResponseEntity<String>(usuarioService.actualizarUsuarioPorId(usuario),HttpStatus.OK);
        }
        else{
            throw new CamposInvalidosException("Error! Los datos del uisuario no pueden estar vacios.");
        }
    }
}
