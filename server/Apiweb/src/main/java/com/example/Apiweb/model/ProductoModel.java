package com.example.Apiweb.model;

import com.example.Apiweb.model.enums.Estado;
import com.example.Apiweb.model.enums.Genero;
import com.example.Apiweb.model.enums.Personalizable;
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
    private String image_Url;

    @Column(name = "Estado")
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Column(name = "Personalizable")
    @Enumerated(EnumType.STRING)
    private Personalizable personalizable;

    @Column(name = "Genero")
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @ManyToOne
    @JoinColumn(name = "idCategoria")
    private CategoriaModel idCategoria;
}
