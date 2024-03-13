package com.Ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
}
