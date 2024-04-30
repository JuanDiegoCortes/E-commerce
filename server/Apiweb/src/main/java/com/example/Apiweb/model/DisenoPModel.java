package com.example.Apiweb.model;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDisenoP;
    private String image_url;
    private Integer disenadorAsignado;

    @ManyToOne
    @JoinColumn(name = "idOrdenProd")
    private OrdenProdModel idOrdenProd;

    @ManyToOne
    @JoinColumn(name = "cedula")
    private UsuarioModel cedula;
}
