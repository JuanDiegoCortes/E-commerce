package com.example.Apiweb.controller;

import com.example.Apiweb.domain.ProductoDTO;
import com.example.Apiweb.exception.CamposInvalidosException;
import com.example.Apiweb.exception.RecursoNoEncontradoException;
import com.example.Apiweb.model.CategoriaModel;
import com.example.Apiweb.model.ProdTallaModel;
import com.example.Apiweb.model.ProductoModel;
import com.example.Apiweb.model.TallaModel;
import com.example.Apiweb.model.enums.Estado;
import com.example.Apiweb.model.enums.Genero;
import com.example.Apiweb.service.ICategoriaService;
import com.example.Apiweb.service.IProdTallaService;
import com.example.Apiweb.service.IProductoService;
import com.example.Apiweb.service.ITallaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Apiweb/v1/producto")
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
        boolean bandera = true;
        CategoriaModel categoria = categoriaService.obtenerCategoriaPorId(productoDTO.getIdCategoria().getIdCategoria())
                .orElseThrow(()-> new RecursoNoEncontradoException("La categoria no existe."));

        //Crear instancia de un productoModel
        ProductoModel producto = new ProductoModel();
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setEstado(productoDTO.getEstado());
        producto.setPersonalizable(productoDTO.getPersonalizable());
        producto.setImage_Url(productoDTO.getImage_Url());
        producto.setGenero(productoDTO.getGenero());
        producto.setIdCategoria(categoria);

        //Capturar las tallas y verificar que existan
        if (productoDTO.getTallas() != null) {
            List<Map<String, Integer>> listarTallas = productoDTO.getTallas();
            if (bandera) {
                System.out.println("Bandera: "+ bandera);
                productoService.crearProducto(producto);
                for (Map<String, Integer> Tallas : listarTallas) {
                    Integer idTalla = Tallas.get("idTalla");
                    ProdTallaModel prodTalla = new ProdTallaModel();
                    TallaModel talla = tallaService.obtenerTallaPorId(idTalla).get();
                    prodTalla.setIdProducto(producto);
                    prodTalla.setIdTalla(talla);
                    prodTalla.setCantidad(Tallas.get("cantidad"));
                    this.prodTallaService.crearProdTalla(prodTalla);
                }
                return new ResponseEntity<String>("Producto creado exitosamente.", HttpStatus.OK);
            }
        }else{
            String mensaje = "Verifica que hayas ingresado las tallas del producto.";
            return new ResponseEntity<>(mensaje, HttpStatus.BAD_REQUEST);
        }
        return null;
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
        Estado nombreActualizar4= detallesProducto.getEstado();
        String nombreActualizar5= detallesProducto.getImage_Url();

        //Verificamos que estos campos a actualizar no sean nulos o vacios y controlamos la excepcion
            //asignamos los valores que vamos actualizar del envio
        if (nombreActualizar != null && !nombreActualizar.isEmpty() && nombreActualizar != "") {
            producto.setNombre(nombreActualizar);
        }
        if (nombreActualizar2 != null && !nombreActualizar2.isEmpty() && nombreActualizar2 != "") {
            producto.setDescripcion(nombreActualizar2);
        }
        if (nombreActualizar3 != null) {
            producto.setPrecio(nombreActualizar3);
        }
        if (nombreActualizar4 != null) {
            producto.setEstado(nombreActualizar4);
        }
        if (nombreActualizar5 != null && !nombreActualizar5.isEmpty() && nombreActualizar5 != "") {
            producto.setImage_Url(nombreActualizar5);
        }

        if (nombreActualizar == "" && nombreActualizar2 == "" && nombreActualizar3 == null && nombreActualizar4 == null && nombreActualizar5 == "") {
            return new ResponseEntity<>("Error!. No hay datos para actualizar.", HttpStatus.BAD_REQUEST);
        } else {
            this.productoService.actualizarProductoPorId(producto);
            return new ResponseEntity<>("Producto actualizado con exito.", HttpStatus.OK);
        }
    }

    //Filtrar por genero
    @PutMapping("/{genero}")
    public List<ProductoModel> filtrarPorGenero(@PathVariable Genero genero) {
        return productoService.filtrarPorGenero(genero);
    }

    @PutMapping("/{categoria}")
    public List<ProductoModel> filtrarPorCategoria(@PathVariable CategoriaModel categoria) {
        return productoService.filtrarPorCategoria(categoria);
    }
}
