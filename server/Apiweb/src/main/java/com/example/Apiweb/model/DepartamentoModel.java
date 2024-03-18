package com.Ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Departamento")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartamentoModel {
    @Id
    private Integer idDepartamento;
    private String nombre;
}
