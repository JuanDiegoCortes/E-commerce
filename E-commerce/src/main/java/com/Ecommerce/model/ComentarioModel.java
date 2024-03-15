package com.Ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Comentario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioModel {
    @Id
    private Integer idComentario;
    private String texto;

    @ManyToOne
    @JoinColumn(name = "cedula")
    private UsuarioModel cedula;

    @ManyToOne
    @JoinColumn(name = "subIdComentario")
    private ComentarioModel subIdComentario;

    @ManyToOne
    @JoinColumn(name = "idDiseñoP")
    private DisenoPModel idDiseñoP;

}
