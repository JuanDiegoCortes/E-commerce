package com.Ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Categoria")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaModel {
    @Id
    private Integer idCategoria;
    private String nombre;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "subIdCategoria")
    private CategoriaModel subIdCategoria;
}
