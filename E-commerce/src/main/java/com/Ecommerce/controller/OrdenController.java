package com.Ecommerce.controller;

import com.Ecommerce.domain.OrdenDTO;
import com.Ecommerce.exception.CamposInvalidosException;
import com.Ecommerce.exception.RecursoNoEncontradoException;
import com.Ecommerce.model.*;
import com.Ecommerce.model.enums.Estado;
import com.Ecommerce.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    @Autowired
    private IProductoService productoService;
    @Autowired
    private IOrdenProdService ordenProdService;

    @PostMapping("/")
    public ResponseEntity<String> crearOrden(@RequestBody OrdenDTO ordenDTO) {
        //Verificar si la orden ya existe
        boolean bandera = true;

        Optional<OrdenModel> verificacion = ordenService.obtenerOrdenPorId(ordenDTO.getIdOrden());
        if (verificacion.isPresent()){
            String mensaje = "Esta orden ya existe.";
            return new ResponseEntity<String>(mensaje, HttpStatus.BAD_REQUEST);
        }

        EnvioModel envio = envioService.obtenerEnvioPorId(ordenDTO.getIdEnvio().getIdEnvio())
                .orElseThrow(()-> new RecursoNoEncontradoException("El envio no existe."));
        UsuarioModel usuario = usuarioService.obtenerUsuarioPorId(ordenDTO.getCedula().getCedula())
                .orElseThrow(()-> new RecursoNoEncontradoException("El usuario no existe."));

        //Crear instancia de un ordenModel
        OrdenModel orden = new OrdenModel();
        orden.setCedula(usuario);
        //No se como poner los sets de esta clase ya que son dependientes de otros factores
        //////////////////////////////////////////////////////////////

        //Capturar los productos y verificar que existan
        if (ordenDTO.getProductos() != null) {
            List<Map<String, Integer>> listarProductos = ordenDTO.getProductos();
            for (Map<String, Integer> Productos : listarProductos) {
                Integer idProducto = Productos.get("idProducto");
                ProductoModel producto = this.productoService.obtenerProductoPorId(idProducto)
                        .orElseThrow(() -> new RecursoNoEncontradoException("No esta el producto con id: " + idProducto));
                if (producto == null) {
                    bandera = false;
                    throw new RecursoNoEncontradoException("No se encontró el producto con ID: " + idProducto);
                }
            }
        if (bandera) {
            System.out.println("Bandera: "+ bandera);
            ordenService.crearOrden(orden);
            for (Map<String, Integer> Productos : listarProductos) {
                Integer idProducto = Productos.get("idProducto");
                OrdenProdModel ordenProd = new OrdenProdModel();
                ProductoModel producto = productoService.obtenerProductoPorId(idProducto).get();
                ordenProd.setIdProducto(producto);
                ordenProd.setIdOrden(orden);
                this.ordenProdService.crearOrdenProd(ordenProd);
            }
            return new ResponseEntity<String>(ordenService.crearOrden(orden), HttpStatus.OK);
        } else{
            String mensaje = "Verifica que los datos ingresados sean correctos.";
            return new ResponseEntity<>(mensaje, HttpStatus.BAD_REQUEST); //Envia esto cuando no existen las tallas
            }
        }else{
            String mensaje = "Verifica que hayas ingresado las tallas del producto.";
            return new ResponseEntity<>(mensaje, HttpStatus.BAD_REQUEST);
        }
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
