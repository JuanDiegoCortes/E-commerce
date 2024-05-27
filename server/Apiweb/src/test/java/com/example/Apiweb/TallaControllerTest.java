package com.example.Apiweb;

import com.example.Apiweb.controller.TallaController;
import com.example.Apiweb.model.TallaModel;
import com.example.Apiweb.service.ITallaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TallaControllerTest {
    @InjectMocks
    TallaController tallaController;
    @Mock
    ITallaService tallaService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearTallas() {
        TallaModel talla1 = new TallaModel(1,"S");
        TallaModel talla2 = new TallaModel(2,"M");

        when(tallaService.crearTalla(talla1)).thenReturn(String.valueOf(talla1));
        when(tallaService.crearTalla(talla2)).thenReturn(String.valueOf(talla2));

        ResponseEntity<String> response1 = tallaController.crearTalla(talla1);
        ResponseEntity<String> response2 = tallaController.crearTalla(talla2);

        // Verificar que los métodos del servicio se llamaron
        verify(tallaService, times(1)).crearTalla(talla1);
        verify(tallaService, times(1)).crearTalla(talla2);

        // Verificar los códigos de estado de las respuestas
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals(HttpStatus.OK, response2.getStatusCode());

        // Verificar los cuerpos de las respuestas
        assertEquals(String.valueOf(talla1), response1.getBody());
        assertEquals(String.valueOf(talla2), response2.getBody());
    }
}
