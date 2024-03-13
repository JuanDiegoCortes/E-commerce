package com.Ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="OrdenProdModel")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenProdModel {
    @Id
    @ManyToOne
    @JoinColumn(name = "idOrden")
    private OrdenModel idOrden;

    @ManyToOne
    @JoinColumn(name = "idProducto")
    private ProductoModel idProducto;

    private String cantidad;
    private String ordenPersonalizacion;
}
