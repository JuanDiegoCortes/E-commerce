package com.Ecommerce.controller;

import com.Ecommerce.domain.ProductoDTO;
import com.Ecommerce.exception.CamposInvalidosException;
import com.Ecommerce.exception.RecursoNoEncontradoException;
import com.Ecommerce.model.CategoriaModel;
import com.Ecommerce.model.ProdTallaModel;
import com.Ecommerce.model.ProductoModel;
import com.Ecommerce.model.TallaModel;
import com.Ecommerce.service.ICategoriaService;
import com.Ecommerce.service.IProdTallaService;
import com.Ecommerce.service.IProductoService;
import com.Ecommerce.service.ITallaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/Ecommerce/v1/producto")
@CrossOrigin
public class ProductoController {
    @Autowired
    private IProductoService productoService;
    @Autowired
    private ICategoriaService categoriaService;
    @Autowired
    private ITallaService tallaService;
    @Autowired
    private IProdTallaService prodTallaService;

    @PostMapping("/")
    public ResponseEntity<String> crearProducto(@RequestBody ProductoDTO productoDTO) {
        //Verificar si el producto ya existe
        boolean bandera = true;
        Optional<ProductoModel> verificacion = productoService.obtenerProductoPorId(productoDTO.getIdProducto());
        if (verificacion.isPresent()){
            String mensaje = "Este producto ya existe.";
            return new ResponseEntity<String>(mensaje, HttpStatus.BAD_REQUEST);
        }

        CategoriaModel categoria = categoriaService.obtenerCategoriaPorId(productoDTO.getIdCategoria().getIdCategoria())
                .orElseThrow(()-> new RecursoNoEncontradoException("La categoria no existe."));

        //Crear instancia de un productoModel
        ProductoModel producto = new ProductoModel();
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setIsActive(productoDTO.getIsActive());
        producto.setImage_Url(productoDTO.getImage_Url());
        producto.setGenero(productoDTO.getGenero());
        producto.setTipoProducto(productoDTO.getTipoProducto());
        producto.setIdCategoria(categoria);

        //Capturar las tallas y verificar que existan
        if (productoDTO.getTallas() != null) {
            List<Map<String, Integer>> listarTallas = productoDTO.getTallas();
            for (Map<String, Integer> Tallas : listarTallas) {
                Integer idTalla = Tallas.get("idTalla");
                TallaModel talla = this.tallaService.obtenerTallaPorId(idTalla)
                        .orElseThrow(() -> new RecursoNoEncontradoException("No esta la talla con id: " + idTalla));
                if (talla == null) {
                    bandera = false;
                    throw new RecursoNoEncontradoException("No se encontró la talla con ID: " + idTalla);
                }
            }
            if (bandera) {
                System.out.println("Bandera: "+ bandera);
                productoService.crearProducto(producto);
                for (Map<String, Integer> Tallas : listarTallas) {
                    Integer idTalla = Tallas.get("idTalla");
                    ProdTallaModel prodTalla = new ProdTallaModel();
                    TallaModel talla = tallaService.obtenerTallaPorId(idTalla).get();
                    prodTalla.setIdProducto(producto);
                    prodTalla.setIdTalla(talla);
                    prodTalla.setCantidad(prodTalla.getCantidad());
                    this.prodTallaService.crearProdTalla(prodTalla);
                }
                return new ResponseEntity<String>(productoService.crearProducto(producto), HttpStatus.OK);
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
    public ResponseEntity<List<ProductoModel>> listarProductos(){
        List<ProductoModel> productos = productoService.listarProductos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    //consultar un producto por Id
    @GetMapping("/{productoId}")
    public ResponseEntity<ProductoModel> obtenerProductoPorId(@PathVariable Integer productoId) {
        ProductoModel producto = this.productoService.obtenerProductoPorId(productoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró el producto con el id " + productoId));
        return ResponseEntity.ok(producto);
    }

    //actualizar la información básica del producto
    @PutMapping ("/{productoId}")
    public ResponseEntity<String> actualizarProductoPorId(@PathVariable Integer productoId, @RequestBody ProductoModel detallesProducto) {
        ProductoModel producto = this.productoService.obtenerProductoPorId(productoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró el prodcuto con el id " + productoId));
        //obtenemos los datos que se van actualizar del producto y que son enviados del json
        String nombreActualizar = detallesProducto.getNombre();
        String nombreActualizar2 = detallesProducto.getDescripcion();
        Float nombreActualizar3= detallesProducto.getPrecio();
        Boolean nombreActualizar4= detallesProducto.getIsActive();
        String nombreActualizar5= detallesProducto.getImage_Url();

        //Verificamos que estos campos a actualizar no sean nulos o vacios y controlamos la excepcion
        if (nombreActualizar !=null && !nombreActualizar.isEmpty() && nombreActualizar2 != null && !nombreActualizar2.isEmpty() && nombreActualizar3 != null && nombreActualizar4 != null && nombreActualizar5 !=null && !nombreActualizar5.isEmpty()){
            //asignamos los valores que vamos actualizar del envio
            producto.setNombre(nombreActualizar);
            producto.setDescripcion(nombreActualizar2);
            producto.setPrecio(nombreActualizar3);
            producto.setIsActive(nombreActualizar4);
            producto.setImage_Url(nombreActualizar5);

            //guardamos los cambios
            return new ResponseEntity<String>(productoService.actualizarProductoPorId(producto),HttpStatus.OK);
        }
        else{
            throw new CamposInvalidosException("Error! El nombre, la descripción, el precio, el estado y la Imagen URL del producto no pueden estar vacios");
        }
    }
}
