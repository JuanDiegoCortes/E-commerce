package com.Ecommerce.model;

import com.Ecommerce.model.enums.Estado;
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
    private Integer idOrden;
    private Date fecha;

    @Column(name="Estado")
    @Enumerated(EnumType.STRING)
    private Estado estado;
    private String metodoPago;
    private Float precioTotal;

    @ManyToOne
    @JoinColumn(name = "idEnvio")
    private EnvioModel idEnvio;

    @ManyToOne
    @JoinColumn(name = "cedula")
    private UsuarioModel cedula;

}
