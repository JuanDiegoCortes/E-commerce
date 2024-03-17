package com.Ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Ciudad")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CiudadModel {
    @Id
    private Integer idCiudad;
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "idDepartamento")
    private DepartamentoModel idDepartamento;
}
