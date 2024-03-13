package com.Ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioModel {
    @Id
    private Integer cedula;
    private String nombre;
    private String correo;
    private String contraseña;

    @ManyToOne
    @JoinColumn(name = "idRol")
    private RolModel idRol;
}
