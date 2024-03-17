package com.Ecommerce.domain;

import com.Ecommerce.model.CategoriaModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
