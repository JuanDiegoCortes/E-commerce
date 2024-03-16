package com.Ecommerce.controller;

import com.Ecommerce.exception.CamposInvalidosException;
import com.Ecommerce.exception.RecursoNoEncontradoException;
import com.Ecommerce.model.EnvioModel;
import com.Ecommerce.model.OrdenModel;
import com.Ecommerce.model.UsuarioModel;
import com.Ecommerce.model.enums.Estado;
import com.Ecommerce.service.IEnvioService;
import com.Ecommerce.service.IOrdenService;
import com.Ecommerce.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Ecommerce/v1/orden")
public class OrdenController {
    @Autowired
    private IOrdenService ordenService;
    @Autowired
    private IEnvioService envioService;
    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping("/")
    public ResponseEntity<String> crearOrden(@RequestBody OrdenModel orden) {
        //Verificar si la orden ya existe
        EnvioModel envio = envioService.obtenerEnvioPorId(orden.getIdEnvio().getIdEnvio())
                .orElseThrow(()-> new RecursoNoEncontradoException("El envio no existe."));
        UsuarioModel usuario = usuarioService.obtenerUsuarioPorId(orden.getCedula().getCedula())
                .orElseThrow(()-> new RecursoNoEncontradoException("El usuario no existe."));

        Optional<OrdenModel> verificacion = ordenService.obtenerOrdenPorId(orden.getIdOrden());
        if (verificacion.isPresent()){
            String mensaje = "Esta orden ya existe.";
            return new ResponseEntity<String>(mensaje, HttpStatus.BAD_REQUEST);
        }
        ordenService.crearOrden(orden);
        return new ResponseEntity<String>(ordenService.crearOrden(orden), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<OrdenModel>> listarOrdenes(){
        List<OrdenModel> ordenes = ordenService.listarOrdenes();
        return new ResponseEntity<>(ordenes, HttpStatus.OK);
    }

    //consultar una orden por Id
    @GetMapping("/{ordenId}")
    public ResponseEntity<OrdenModel> obtenerOrdenPorId(@PathVariable Integer ordenId) {
        OrdenModel orden = this.ordenService.obtenerOrdenPorId(ordenId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró la orden con el id " + ordenId));
        return ResponseEntity.ok(orden);
    }

    //actualizar la información básica del envío
    @PutMapping ("/{ordenId}")
    public ResponseEntity<String> actualizarOrdenPorId(@PathVariable Integer ordenId, @RequestBody OrdenModel detallesOrden) {
        OrdenModel orden = this.ordenService.obtenerOrdenPorId(ordenId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró la orden con el id " + ordenId));
        //obtenemos los datos que se van actualizar del envío y que son enviados del json
        Estado nombreActualizar = detallesOrden.getEstado();
        String nombreActualizar2 = detallesOrden.getMetodoPago();
        Float nombreActualizar3= detallesOrden.getPrecioTotal();


        //Verificamos que estos campos a actualizar no sean nulos o vacios y controlamos la excepcion
        if (nombreActualizar !=null && nombreActualizar2 != null && !nombreActualizar2.isEmpty() && nombreActualizar3 != null){
            //asignamos los valores que vamos actualizar del envio
            orden.setEstado(nombreActualizar);
            orden.setMetodoPago(nombreActualizar2);
            orden.setPrecioTotal(nombreActualizar3);
            //guardamos los cambios
            return new ResponseEntity<String>(ordenService.actualizarOrdenPorId(orden),HttpStatus.OK);
        }
        else{
            throw new CamposInvalidosException("Error! El estado, el método de pago  y el precio total de la orden no pueden estar vacios");
        }
    }
}
