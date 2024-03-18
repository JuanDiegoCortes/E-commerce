package com.example.Apiweb.domain;

import com.example.Apiweb.model.CategoriaModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {
    private Integer idCategoria;
    private String nombre;
    private String descripcion;
    private CategoriaModel subIdCategoria;

    @JsonProperty("subCategorias")
    List<Map<String, Integer>> subCategorias;
}
