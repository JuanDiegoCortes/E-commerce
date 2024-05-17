package com.example.Apiweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="OrdenProd")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenProdModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrdenProd;
    private Integer cantidad;
    private String image_Personalizacion;
    private String texto_Personalizacion;


    @ManyToOne
    @JoinColumn(name = "idOrden")
    private OrdenModel idOrden;

    @ManyToOne
    @JoinColumn(name = "idProducto")
    private ProductoModel idProducto;
}
