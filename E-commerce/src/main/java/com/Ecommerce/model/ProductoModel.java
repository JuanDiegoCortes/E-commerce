package com.Ecommerce.model;

import com.Ecommerce.model.enums.Genero;
import com.Ecommerce.model.enums.TipoProducto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Producto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;
    private String nombre;
    private String descripcion;
    private Float precio;
    private Boolean isActive;
    private String image_Url;

    @Column(name = "Genero")
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Column(name = "tipoProducto")
    @Enumerated(EnumType.STRING)
    private TipoProducto tipoProducto;

    @ManyToOne
    @JoinColumn(name = "idCategoria")
    private CategoriaModel idCategoria;
}
