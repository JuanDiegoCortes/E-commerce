package com.example.Apiweb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Rol")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolModel {
    @Id
    private Integer idRol;
    private String nombre;
}
