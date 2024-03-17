package com.Ecommerce.model;

import com.Ecommerce.model.enums.Estado;
import com.Ecommerce.model.enums.MetodoPago;
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

    @Column(name="Estado")
    @Enumerated(EnumType.STRING)
    private Estado estado;

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
