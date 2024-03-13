package com.Ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="DiseñoP")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiseñoPModel {
    @Id
    private Integer idDiseñoP;
    private String image_url;

    @ManyToOne
    @JoinColumn(name = "idOrden")
    private OrdenModel idOrden;

    @ManyToOne
    @JoinColumn(name = "cedula")
    private UsuarioModel cedula;
}
