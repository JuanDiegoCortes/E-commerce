package com.example.Apiweb.controller;

import com.example.Apiweb.exception.RecursoNoEncontradoException;
import com.example.Apiweb.model.EvidenciaPagoModel;
import com.example.Apiweb.service.IEvidenciaPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Apiweb/v1/evidenciaPago")
@CrossOrigin
public class EvidenciaPagoController {
    @Autowired
    private IEvidenciaPagoService evidenciaPagoService;

    @PostMapping("/")
    public ResponseEntity<String> crearEvidenciaPago(@RequestBody EvidenciaPagoModel evidenciaPago) {
        evidenciaPagoService.crearEvidenciaPago(evidenciaPago);
        return new ResponseEntity<String>(evidenciaPagoService.crearEvidenciaPago(evidenciaPago), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<EvidenciaPagoModel>> listarEvidenciasPago(){
        List<EvidenciaPagoModel> evidenciasPago = evidenciaPagoService.listarEvidenciasPago();
        return new ResponseEntity<>(evidenciasPago, HttpStatus.OK);
    }

    //consultar un envio por Id
    @GetMapping("/{evidenciaPagoId}")
    public ResponseEntity<EvidenciaPagoModel> obtenerEvidenciaPagoPorId(@PathVariable Integer evidenciaPagoId) {
        EvidenciaPagoModel evidenciaPago = this.evidenciaPagoService.obtenerEvidenciaPagoPorId(evidenciaPagoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró la evidencia de pago con el id " + evidenciaPagoId));
        return ResponseEntity.ok(evidenciaPago);
    }
}
