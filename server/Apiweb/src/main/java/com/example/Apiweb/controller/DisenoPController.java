package com.example.Apiweb.controller;

import com.example.Apiweb.model.DisenoPModel;
import com.example.Apiweb.service.IDisenoPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Apiweb.exception.CamposInvalidosException;
import com.example.Apiweb.exception.RecursoNoEncontradoException;

import java.util.List;

@RestController
@RequestMapping("/Apiweb/v1/disenoP")
@CrossOrigin
public class DisenoPController {
    @Autowired
    private IDisenoPService disenoPService;
    
    @PostMapping("/")
    public ResponseEntity<String> crearDisenoP(@RequestBody DisenoPModel disenoP) {
        disenoPService.crearDisenoP(disenoP);
        return new ResponseEntity<String>(disenoPService.crearDisenoP(disenoP), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<DisenoPModel>> listarDisenosP(){
        List<DisenoPModel> disenosP = disenoPService.listarDisenosP();
        return new ResponseEntity<>(disenosP, HttpStatus.OK);
    }

    //consultar un disenoP por Id
    @GetMapping("/{disenoPId}")
    public ResponseEntity<DisenoPModel> obtenerDisenoPPorId(@PathVariable Integer disenoPId) {
        DisenoPModel diseno = this.disenoPService.obtenerDisenoPPorId(disenoPId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró el disenoP con el id " + disenoPId));
        return ResponseEntity.ok(diseno);
    }

    //actualizar la información básica del disenoP
    @PutMapping ("/{disenoPId}")
    public ResponseEntity<String> actualizarDisenoPPorId(@PathVariable Integer disenoPId, @RequestBody DisenoPModel detallesDisenoP) {
        DisenoPModel disenoP = this.disenoPService.obtenerDisenoPPorId(disenoPId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró el disenoP con el id " + disenoPId));
        //obtenemos los datos que se van actualizar del disenoP y que son enviados del json
        String nombreActualizar = detallesDisenoP.getImage_url();

        //Verificamos que estos campos a actualizar no sean nulos o vacios y controlamos la excepcion
        if (nombreActualizar !=null && !nombreActualizar.isEmpty()){
            //asignamos los valores que vamos actualizar del envio
            disenoP.setImage_url(nombreActualizar);
            //guardamos los cambios
            return new ResponseEntity<String>(disenoPService.actualizarDisenoPPorId(disenoP),HttpStatus.OK);
        }
        else{
            throw new CamposInvalidosException("Error! La url no puede estar vacia.");
        }
    }

    @GetMapping("/visualizarDisenoP/{idOrdenProd}")
    public ResponseEntity<DisenoPModel> visualizarDiseno(@PathVariable Integer idOrdenProd) {
        DisenoPModel diseno = this.disenoPService.mostrarDisenoCompartidoPorIdOrden(idOrdenProd)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró el disenoP con el idOrden " + idOrdenProd));
        return ResponseEntity.ok(diseno);
    }

    @PutMapping("/cambiarEstadoDisenoP/{idOrdenProd}/{estado}")
    public ResponseEntity<String> cambiarEstadoDiseno(@PathVariable Integer idOrdenProd, @PathVariable String estado) {
        disenoPService.cambiarEstadoDisenoPorIdOrdenProd(idOrdenProd, estado);
        return new ResponseEntity<String>("Estado de el disenoP actualizado correctamente.", HttpStatus.OK);
    }

    @PutMapping("/actualizarImagen/{idDisenoP}")
    public ResponseEntity<String> actualizarImagen(@PathVariable Integer idDisenoP, @RequestBody String image_url) {
        return new ResponseEntity<String>(disenoPService.actualizarImagenPorId(idDisenoP,image_url), HttpStatus.OK);
    }
}
