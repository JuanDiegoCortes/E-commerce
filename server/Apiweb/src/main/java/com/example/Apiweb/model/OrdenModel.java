package com.example.Apiweb.model;

import com.example.Apiweb.model.enums.Estado;
import com.example.Apiweb.model.enums.MetodoPago;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="Orden")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrden;
    private Date fecha;
    private Integer disenadorAsignado;

    @Column(name="Estado")
    @Enumerated(EnumType.STRING)
    private Estado estado;

    private String image_Evidencia;
    private Float precioTotal;

    @Column(name = "MetodoPago")
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    @ManyToOne
    @JoinColumn(name = "idEnvio")
    private EnvioModel idEnvio;

    @ManyToOne
    @JoinColumn(name = "cedula")
    private UsuarioModel cedula;

}
