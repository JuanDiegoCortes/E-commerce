package com.example.Apiweb.controller;

import com.example.Apiweb.domain.OrdenDTO;
import com.example.Apiweb.exception.CamposInvalidosException;
import com.example.Apiweb.exception.RecursoNoEncontradoException;
import com.example.Apiweb.model.*;
import com.example.Apiweb.model.enums.Estado;
import com.example.Apiweb.model.enums.MetodoPago;
import com.example.Apiweb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Apiweb/v1/orden")
@CrossOrigin
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
    @Autowired
    private IProdTallaService prodTallaService;

    @PostMapping("/")
    public ResponseEntity<String> crearOrden(@RequestBody OrdenDTO ordenDTO) {
        //Verificar si la orden ya existe
        boolean bandera = true;

        // Obtener la fecha actual
        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fechaFormateada = formato.format(fechaActual);
        Date fechaFormateadaDate = null;
        try {
            fechaFormateadaDate = formato.parse(fechaFormateada);
        } catch (Exception e) {
            e.printStackTrace();
        }

        EnvioModel envio = new EnvioModel();
        envio.setNombre(ordenDTO.getIdEnvio().getNombre());
        envio.setApellido(ordenDTO.getIdEnvio().getApellido());
        envio.setDireccion(ordenDTO.getIdEnvio().getDireccion());
        envio.setModalidadEntrega(ordenDTO.getIdEnvio().getModalidadEntrega());
        envio.setTelefono(ordenDTO.getIdEnvio().getTelefono());
        envio.setCodigoPostal(ordenDTO.getIdEnvio().getCodigoPostal());
        envio.setReferencias(ordenDTO.getIdEnvio().getReferencias());
        envio.setIdCiudad(ordenDTO.getIdEnvio().getIdCiudad());
        envioService.crearEnvio(envio);

        UsuarioModel usuario = usuarioService.obtenerUsuarioPorId(ordenDTO.getCedula().getCedula())
                .orElseThrow(() -> new RecursoNoEncontradoException("El usuario no existe."));

        //Crear instancia de un ordenModel
        OrdenModel orden = new OrdenModel();
        orden.setFecha(fechaFormateadaDate);
        orden.setEstado(ordenDTO.getEstado());
        orden.setMetodoPago(ordenDTO.getMetodoPago());
        orden.setPrecioTotal(ordenDTO.getPrecioTotal());
        orden.setIdEnvio(envio);
        orden.setCedula(usuario);

        //Capturar los productos y verificar que existan
        if (ordenDTO.getProductos() != null) {
            List<Map<String, Object>> listarProductos = ordenDTO.getProductos();
            for (Map<String, Object> Productos : listarProductos) {
                Integer cantidad = (Integer) Productos.get("cantidad");
                Integer idTalla = (Integer) Productos.get("idTalla");

                ProdTallaModel talla = this.prodTallaService.obtenerProdTallaPorId(idTalla)
                        .orElseThrow(() -> new RecursoNoEncontradoException("No esta la talla con id: " + idTalla));
                ProductoModel productito = this.productoService.obtenerProductoPorId((Integer) Productos.get("idProducto"))
                        .orElseThrow(() -> new RecursoNoEncontradoException("No esta el producto con id: " + Productos.get("idProducto")));

                if (talla.getCantidad() < cantidad) {
                    bandera = false;
                    return new ResponseEntity<String>("No hay suficientes stock en la talla " + talla.getIdTalla().getMedida() + " seleccionada para " + productito.getNombre() + " (Cantidad: " + talla.getCantidad() + ")", HttpStatus.BAD_REQUEST);
                }
            }
            if (bandera) {
                System.out.println("Bandera: " + bandera);
                ordenService.crearOrden(orden);
                for (Map<String, Object> Productos : listarProductos) {
                    Integer idProducto = (Integer) Productos.get("idProducto");
                    Integer idTalla = (Integer) Productos.get("idTalla");
                    OrdenProdModel ordenProd = new OrdenProdModel();
                    Integer cantidad = (Integer) Productos.get("cantidad");
                    String image_Personalizacion = (String) Productos.get("image_Personalizacion");
                    String texto_Personalizacion = (String) Productos.get("texto_Personalizacion");
                    ProductoModel producto = productoService.obtenerProductoPorId(idProducto).get();
                    ordenProd.setCantidad(cantidad);
                    ordenProd.setImage_Personalizacion(image_Personalizacion);
                    ordenProd.setTexto_Personalizacion(texto_Personalizacion);
                    ordenProd.setIdProducto(producto);
                    ordenProd.setIdOrden(orden);

                    //Actualizar la cantidad de productos en la talla
                    ProdTallaModel prodTalla = this.prodTallaService.obtenerProdTallaPorProductoYTalla(idProducto, idTalla);
                    Integer Resultado = prodTalla.getCantidad() - cantidad;
                    System.out.println(Resultado);
                    prodTallaService.actualizarCantidadProdTalla(Resultado, prodTalla.getIdProducto().getIdProducto(), prodTalla.getIdTalla().getIdTalla());

                    ordenProdService.crearOrdenProd(ordenProd);
                }
                return new ResponseEntity<String>("Orden creada correctamente. Redirigiendo...", HttpStatus.OK);
            }
        } else {
            String mensaje = "Verifica que hayas ingresado laos datos de la orden.";
            return new ResponseEntity<>(mensaje, HttpStatus.BAD_REQUEST);
        }
        return null;
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

    //actualizar la información básica de la orden
    @PutMapping ("/{ordenId}")
    public ResponseEntity<String> actualizarOrdenPorId(@PathVariable Integer ordenId, @RequestBody OrdenModel detallesOrden) {
        OrdenModel orden = this.ordenService.obtenerOrdenPorId(ordenId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró la orden con el id " + ordenId));
        //obtenemos los datos que se van actualizar del envío y que son enviados del json
        Date nombreActualizar = detallesOrden.getFecha();
        Estado nombreActualizar2 = detallesOrden.getEstado();
        MetodoPago nombreActualizar3 = detallesOrden.getMetodoPago();
        Float nombreActualizar4 = detallesOrden.getPrecioTotal();

        //Verificamos que estos campos a actualizar no sean nulos o vacios y controlamos la excepcion
        if (nombreActualizar !=null && nombreActualizar2 != null && nombreActualizar3 != null && nombreActualizar4 != null){
            //asignamos los valores que vamos actualizar del envio
            orden.setFecha(nombreActualizar);
            orden.setEstado(nombreActualizar2);
            orden.setMetodoPago(nombreActualizar3);
            orden.setPrecioTotal(nombreActualizar4);
            //guardamos los cambios
            return new ResponseEntity<String>(ordenService.actualizarOrdenPorId(orden),HttpStatus.OK);
        }
        else{
            throw new CamposInvalidosException("Error! El estado, el método de pago  y el precio total de la orden no pueden estar vacios");
        }
    }

    @PutMapping ("/agregarEvidenciaPagoOrden/{ordenIdEvidencia}")
    public ResponseEntity<String> agregarEvidenciaPagoOrden(@PathVariable Integer ordenIdEvidencia, @RequestBody OrdenModel detallesOrden) {
        OrdenModel orden = this.ordenService.obtenerOrdenPorId(ordenIdEvidencia)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró la orden con el id " + ordenIdEvidencia));
        //obtenemos los datos que se van actualizar de la orden y que son enviados del json
        String pagoActualizar = detallesOrden.getImage_Evidencia();

        //Verificamos que estos campos a actualizar no sean nulos o vacios y controlamos la excepcion
        if (pagoActualizar !=null){
            //asignamos los valores que vamos actualizar de la orden
            orden.setImage_Evidencia(pagoActualizar);
            //guardamos los cambios
            return new ResponseEntity<String>(ordenService.agregarEvidenciaPagoOrden(orden),HttpStatus.OK);
        }
        else{
            throw new CamposInvalidosException("Error! La evidencia de pago de la orden no puede estar vacia");
        }
    }

    @GetMapping("/visualizarOrdenes/{cedula}")
    public ResponseEntity<List<OrdenModel>> visualizarOrdenes(@PathVariable Integer cedula){
        List<OrdenModel> ordenes = ordenService.mostrarOrdenesPorCedula(cedula);
        return new ResponseEntity<List<OrdenModel>>(ordenes, HttpStatus.OK);
    }

    @PutMapping("/actualizarEstadoOrden/{idOrden}/{estado}")
    public ResponseEntity<String> actualizarEstadoOrden(@PathVariable Integer idOrden, @PathVariable Estado estado) {
        return new ResponseEntity<String>(ordenService.actualizarEstadoOrden(idOrden, estado), HttpStatus.OK);
    }

    @PutMapping("/asignarDisenador/{idOrden}/{disenadorAsignado}")
    public ResponseEntity<Integer> asignarDisenador(@PathVariable Integer idOrden, @PathVariable Integer disenadorAsignado) {
        return new ResponseEntity<Integer>(ordenService.asignarDisenador(idOrden, disenadorAsignado), HttpStatus.OK);
    }

}