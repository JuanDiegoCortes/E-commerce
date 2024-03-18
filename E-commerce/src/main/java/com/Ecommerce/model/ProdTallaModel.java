package com.Ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ProdTalla")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdTallaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProdTalla;

    @ManyToOne
    @JoinColumn(name = "idProducto")
    private ProductoModel idProducto;

    @ManyToOne
    @JoinColumn(name = "idTalla")
    private TallaModel idTalla;

    private Integer cantidad;
}
