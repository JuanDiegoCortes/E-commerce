package com.Ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="CiudDepto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CiudDeptoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCiudDepto;

    @ManyToOne
    @JoinColumn(name = "idCiudad")
    private CiudadModel idCiudad;

    @ManyToOne
    @JoinColumn(name = "idDepartamento")
    private DepartamentoModel idDepartamento;
}
