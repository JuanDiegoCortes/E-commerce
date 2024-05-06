package com.example.Apiweb;

import com.example.Apiweb.controller.UsuarioController;
import com.example.Apiweb.model.UsuarioModel;
import com.example.Apiweb.service.IUsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UsuarioControllerTest {
    @InjectMocks
    UsuarioController usuarioController;
    @Mock
    IUsuarioService usuarioService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearUsuarios() {
        UsuarioModel usuario1 = new UsuarioModel(1108638203,"Alejandro","correoelectronicoX@gmail.com");

        when(usuarioService.crearUsuario(usuario1)).thenReturn(String.valueOf(usuario1));

        ResponseEntity<String> response1 = usuarioController.crearUsuario(usuario1);

        // Verificar que el método del servicio se haya llamado
        verify(usuarioService, times(2)).crearUsuario(usuario1);

        // Verificar el código de estado de la respuesta
        assertEquals(HttpStatus.OK, response1.getStatusCode());

        // Verificar el cuerpo de la respuesta
        assertEquals(String.valueOf(usuario1), response1.getBody());
    }
}
