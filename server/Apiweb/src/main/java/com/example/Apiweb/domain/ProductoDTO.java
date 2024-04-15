package com.example.Apiweb.domain;

import com.example.Apiweb.model.CategoriaModel;
import com.example.Apiweb.model.enums.Estado;
import com.example.Apiweb.model.enums.Genero;
import com.example.Apiweb.model.enums.Personalizable;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    private Integer idProducto;
    private String nombre;
    private String descripcion;
    private Float precio;
    private String image_Url;
    private Estado estado;
    private Personalizable personalizable;
    private Genero genero;
    private CategoriaModel idCategoria;

    @JsonProperty("Tallas")
    List<Map<String, Integer>> Tallas;
}
