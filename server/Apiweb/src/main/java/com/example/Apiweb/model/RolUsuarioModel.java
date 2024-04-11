package com.example.Apiweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="RolUsuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolUsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRolUsuario;

    @ManyToOne
    @JoinColumn(name = "cedula")
    private UsuarioModel cedula;

    @ManyToOne
    @JoinColumn(name = "idRol")
    private RolModel idRol;

    private String contrasena;
}
