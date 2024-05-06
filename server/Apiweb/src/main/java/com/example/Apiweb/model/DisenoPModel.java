package com.example.Apiweb.model;


import com.example.Apiweb.model.enums.Estado;
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

    @Column(name="Estado")
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "idOrdenProd")
    private OrdenProdModel idOrdenProd;

    @ManyToOne
    @JoinColumn(name = "cedula")
    private UsuarioModel cedula;
}
