package com.example.Apiweb.model;

import com.example.Apiweb.model.enums.ModalidadEntrega;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Envio")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEnvio;
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private String codigoPostal;
    private String referencias;

    @Column(name = "ModalidadEntrega")
    @Enumerated(EnumType.STRING)
    private ModalidadEntrega modalidadEntrega;

    @ManyToOne
    @JoinColumn(name = "idCiudad")
    private CiudadModel idCiudad;

}
