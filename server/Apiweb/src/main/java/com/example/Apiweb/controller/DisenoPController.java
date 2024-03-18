package com.example.Apiweb.controller;

import com.example.Apiweb.model.DisenoPModel;
import com.example.Apiweb.service.IDisenoPService;
import com.example.Apiweb.service.IOrdenService;
import com.example.Apiweb.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Apiweb.exception.CamposInvalidosException;
import com.example.Apiweb.exception.RecursoNoEncontradoException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Apiweb/v1/disenoP")
@CrossOrigin
public class DisenoPController {
    @Autowired
    private IDisenoPService disenoPService;
    @Autowired
    private IOrdenService ordenService;
    @Autowired
    private IUsuarioService usuarioService;
    
    @PostMapping("/")
    public ResponseEntity<String> crearDisenoP(@RequestBody DisenoPModel disenoP) {
        //Verificar si el envio ya existe
        ordenService.obtenerOrdenPorId(disenoP.getIdOrden().getIdOrden())
                .orElseThrow(()-> new RecursoNoEncontradoException("La orden no existe."));
        usuarioService.obtenerUsuarioPorId(disenoP.getCedula().getCedula())
                .orElseThrow(()-> new RecursoNoEncontradoException("El usuario no existe."));

        Optional<DisenoPModel> verificacion = disenoPService.obtenerDisenoPPorId(disenoP.getIdDisenoP());
        if (verificacion.isPresent()){
            String mensaje = "Este diseñoP ya existe.";
            return new ResponseEntity<String>(mensaje, HttpStatus.BAD_REQUEST);
        }
        disenoPService.crearDisenoP(disenoP);
        return new ResponseEntity<String>(disenoPService.crearDisenoP(disenoP), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<DisenoPModel>> listarDisenosP(){
        List<DisenoPModel> disenosP = disenoPService.listarDisenosP();
        return new ResponseEntity<>(disenosP, HttpStatus.OK);
    }

    //consultar un disenoP por Id
    @GetMapping("/{disenoP}")
    public ResponseEntity<DisenoPModel> obtenerDisenoPPorId(@PathVariable Integer disenoId) {
        DisenoPModel diseno = this.disenoPService.obtenerDisenoPPorId(disenoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró el disenoP con el id " + disenoId));
        return ResponseEntity.ok(diseno);
    }

    //actualizar la información básica del disenoP
    @PutMapping ("/{disenoP}")
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
}
