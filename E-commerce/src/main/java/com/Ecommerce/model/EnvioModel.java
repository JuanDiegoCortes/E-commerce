package com.Ecommerce.model;

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
    private Integer idEnvio;
    private String direccion;
    private String modalidadEntrega;
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "idCiudad")
    private CiudadModel idCiudad;

}
