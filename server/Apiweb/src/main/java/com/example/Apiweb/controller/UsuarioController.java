package com.example.Apiweb.controller;


import com.example.Apiweb.domain.UsuarioDTO;
import com.example.Apiweb.exception.CamposInvalidosException;
import com.example.Apiweb.exception.RecursoNoEncontradoException;
import com.example.Apiweb.model.RolModel;
import com.example.Apiweb.model.RolUsuarioModel;
import com.example.Apiweb.model.UsuarioModel;
import com.example.Apiweb.service.IRolService;
import com.example.Apiweb.service.IRolUsuarioService;
import com.example.Apiweb.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/Apiweb/v1/usuario")
@CrossOrigin
public class UsuarioController {
    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private IRolService rolService;
    @Autowired
    private IRolUsuarioService rolUsuarioService;

    @PostMapping("/")
    public ResponseEntity<String> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        boolean bandera = true;

        String verificarContrasena = (String) usuarioDTO.getRoles().get(0).get("contrasena");
        Optional<UsuarioModel> verificarCorreoUsuario = usuarioService.mostrarUsuarioPorCorreo(usuarioDTO.getCorreo());
        Optional<UsuarioModel> verificarUsuario = usuarioService.obtenerUsuarioPorId(usuarioDTO.getCedula());

        if (verificarUsuario.isPresent()) {
            if (usuarioDTO.getRoles() != null && verificarContrasena != null && verificarCorreoUsuario != null) {
                Optional<RolUsuarioModel> verificarRolUsuario = rolUsuarioService.mostrarRolUsuarioPorCedula(usuarioDTO.getCedula());
                if (verificarRolUsuario.isPresent()) {
                    if (verificarRolUsuario.get().getIdRol().getIdRol() == usuarioDTO.getRoles().get(0).get("idRol")) {
                        return new ResponseEntity<>("El usuario ya existe con el mismo rol.", HttpStatus.OK);
                    } else if (verificarContrasena.equals(verificarRolUsuario.get().getContrasena())) {
                        return new ResponseEntity<>("El usuario ya existe con la misma contraseña.", HttpStatus.OK);
                    }
                }
                List<Map<String, Object>> listarRoles = usuarioDTO.getRoles();
                for (Map<String, Object> roles : listarRoles) {
                    Integer idRol = (Integer) roles.get("idRol");
                    RolModel rol = this.rolService.obtenerRolPorId(idRol)
                            .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el rol con ID: " + idRol));
                    if (rol == null) {
                        bandera = false;
                        throw new RecursoNoEncontradoException("No se encontró el rol con ID: " + idRol);
                    }
                }
                if (bandera){
                    for (Map<String, Object> roles : listarRoles) {
                        Integer idRol = (Integer) roles.get("idRol");
                        RolModel rol = this.rolService.obtenerRolPorId(idRol)
                                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el rol con ID: " + idRol));
                        String contrasena = (String) roles.get("contrasena");

                        RolUsuarioModel rolUsuario = new RolUsuarioModel();
                        rolUsuario.setCedula(verificarUsuario.get());
                        rolUsuario.setIdRol(rol);
                        rolUsuario.setContrasena(contrasena);
                        rolUsuarioService.crearRolUsuario(rolUsuario);
                    }
                    return new ResponseEntity<>("Usuario creado exitosamente", HttpStatus.CREATED);
                }
            } else {
                bandera = false;
                throw new CamposInvalidosException("Error! Todos los campos deben estas completos.");
            }
        } else {
            if (verificarCorreoUsuario.isPresent()) {
                return new ResponseEntity<>("El correo ya esta en uso.", HttpStatus.OK);
            }
            UsuarioModel usuario = new UsuarioModel();
            usuario.setCedula(usuarioDTO.getCedula());
            usuario.setNombre(usuarioDTO.getNombre());
            usuario.setCorreo(usuarioDTO.getCorreo());

            if (usuarioDTO.getRoles() != null) {
                List<Map<String, Object>> listarRoles = usuarioDTO.getRoles();
                for (Map<String, Object> roles : listarRoles) {
                    Integer idRol = (Integer) roles.get("idRol");
                    RolModel rol = this.rolService.obtenerRolPorId(idRol)
                            .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el rol con ID: " + idRol));
                    if (rol == null) {
                        bandera = false;
                        throw new RecursoNoEncontradoException("No se encontró el rol con ID: " + idRol);
                    }
                }
                if (bandera){
                    usuarioService.crearUsuario(usuario);
                    for (Map<String, Object> roles : listarRoles) {
                        Integer idRol = (Integer) roles.get("idRol");
                        RolModel rol = this.rolService.obtenerRolPorId(idRol)
                                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el rol con ID: " + idRol));
                        String contrasena = (String) roles.get("contrasena");

                        RolUsuarioModel rolUsuario = new RolUsuarioModel();
                        rolUsuario.setCedula(usuario);
                        rolUsuario.setIdRol(rol);
                        rolUsuario.setContrasena(contrasena);
                        rolUsuarioService.crearRolUsuario(rolUsuario);
                    }
                    return new ResponseEntity<>("Usuario creado exitosamente", HttpStatus.CREATED);
                }
            } else {
                bandera = false;
                throw new CamposInvalidosException("Error! Los roles no pueden estar vacios.");
            }
        }
        return null;
    }

    @PostMapping("/registroUsuario/")
    public ResponseEntity<String> registroUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        boolean bandera = true;

        Optional<UsuarioModel> verificarUsuario = usuarioService.obtenerUsuarioPorId(usuarioDTO.getCedula());
        Optional<UsuarioModel> verificarCorreoUsuario = usuarioService.mostrarUsuarioPorCorreo(usuarioDTO.getCorreo());

        if (verificarUsuario.isPresent()) {
            return new ResponseEntity<>("El usuario ya existe.", HttpStatus.BAD_REQUEST);
        } else if (verificarCorreoUsuario.isPresent()) {
            return new ResponseEntity<>("El correo ya esta en uso.", HttpStatus.BAD_REQUEST);
        } else {
            UsuarioModel usuario = new UsuarioModel();
            usuario.setCedula(usuarioDTO.getCedula());
            usuario.setNombre(usuarioDTO.getNombre());
            usuario.setCorreo(usuarioDTO.getCorreo());

            if (usuarioDTO.getRoles() != null) {
                List<Map<String, Object>> listarRoles = usuarioDTO.getRoles();
                for (Map<String, Object> roles : listarRoles) {
                    Integer idRol = (Integer) roles.get("idRol");
                    RolModel rol = this.rolService.obtenerRolPorId(idRol)
                            .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el rol con ID: " + idRol));
                    if (rol == null) {
                        bandera = false;
                        throw new RecursoNoEncontradoException("No se encontró el rol con ID: " + idRol);
                    }
                }
                if (bandera) {
                    usuarioService.crearUsuario(usuario);
                    for (Map<String, Object> roles : listarRoles) {
                        Integer idRol = (Integer) roles.get("idRol");
                        RolModel rol = this.rolService.obtenerRolPorId(idRol)
                                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el rol con ID: " + idRol));
                        String contrasena = (String) roles.get("contrasena");

                        RolUsuarioModel rolUsuario = new RolUsuarioModel();
                        rolUsuario.setCedula(usuario);
                        rolUsuario.setIdRol(rol);
                        rolUsuario.setContrasena(contrasena);
                        rolUsuarioService.crearRolUsuario(rolUsuario);
                    }
                    return new ResponseEntity<>("Usuario creado exitosamente", HttpStatus.CREATED);
                }
            }
        }
        return null;
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

    @GetMapping("/obtenerUsuariosPorCorreo/{correo}")
    public ResponseEntity<UsuarioModel> obtenerUsuarioPorCorreo(@PathVariable String correo) {
        UsuarioModel usuario = this.usuarioService.mostrarUsuarioPorCorreo(correo)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró el usuario con el correo: " + correo));
        return ResponseEntity.ok(usuario);
    }
}
