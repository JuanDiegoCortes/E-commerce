package com.example.Apiweb;

import com.example.Apiweb.controller.OrdenController;
import com.example.Apiweb.service.IOrdenService;
import com.example.Apiweb.model.OrdenModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

public class OrdenControllerTest {

    @InjectMocks
    OrdenController ordenController;

    @Mock
    IOrdenService ordenService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarOrdenes() {
        OrdenModel orden1 = new OrdenModel();
        OrdenModel orden2 = new OrdenModel();
        List<OrdenModel> ordenes = Arrays.asList(orden1, orden2);

        when(ordenService.listarOrdenes()).thenReturn(ordenes);

        ResponseEntity<List<OrdenModel>> response = ordenController.listarOrdenes();

        assert response.getStatusCode() == HttpStatus.OK;
    }
}
