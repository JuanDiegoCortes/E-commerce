package com.example.Apiweb.model;

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
    private String contrasena;

    @ManyToOne
    @JoinColumn(name = "idRol")
    private RolModel idRol;
}
