package com.Ecommerce.model;

import com.fasterxml.jackson.databind.annotation.EnumNaming;
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
    @ManyToOne
    @JoinColumn(name = "idProducto")
    private ProductoModel idProducto;

    @ManyToOne
    @JoinColumn(name = "idTalla")
    private TallaModel idTalla;

    private Integer cantidad;
}
