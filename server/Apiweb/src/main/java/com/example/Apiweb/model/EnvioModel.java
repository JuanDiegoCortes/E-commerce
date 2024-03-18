package com.Ecommerce.model;

import com.Ecommerce.model.enums.ModalidadEntrega;
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
    private String direccion;
    private String telefono;

    @Column(name = "ModalidadEntrega")
    @Enumerated(EnumType.STRING)
    private ModalidadEntrega modalidadEntrega;

    @ManyToOne
    @JoinColumn(name = "idCiudad")
    private CiudadModel idCiudad;

}
