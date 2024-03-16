package com.Ecommerce.controller;

import com.Ecommerce.exception.CamposInvalidosException;
import com.Ecommerce.exception.RecursoNoEncontradoException;
import com.Ecommerce.model.CategoriaModel;
import com.Ecommerce.model.ProductoModel;
import com.Ecommerce.service.ICategoriaService;
import com.Ecommerce.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Ecommerce/v1/producto")
public class ProductoController {
    @Autowired
    private IProductoService productoService;
    @Autowired
    private ICategoriaService categoriaService;

    @PostMapping("/")
    public ResponseEntity<String> crearProducto(@RequestBody ProductoModel producto) {
        //Verificar si el producto ya existe
        CategoriaModel categoria = categoriaService.obtenerCategoriaPorId(producto.getIdCategoria().getIdCategoria())
                .orElseThrow(()-> new RecursoNoEncontradoException("La categoria no existe."));

        Optional<ProductoModel> verificacion = productoService.obtenerProductoPorId(producto.getIdProducto());
        if (verificacion.isPresent()){
            String mensaje = "Este producto ya existe.";
            return new ResponseEntity<String>(mensaje, HttpStatus.BAD_REQUEST);
        }
        productoService.crearProducto(producto);
        return new ResponseEntity<String>(productoService.crearProducto(producto), HttpStatus.OK);
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
