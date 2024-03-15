package com.Ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="DisenoP")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisenoPModel {
    @Id
    private Integer idDisenoP;
    private String image_url;

    @ManyToOne
    @JoinColumn(name = "idOrden")
    private OrdenModel idOrden;

    @ManyToOne
    @JoinColumn(name = "cedula")
    private UsuarioModel cedula;
}
