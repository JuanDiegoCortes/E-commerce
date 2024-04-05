package com.example.Apiweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="EvidenciaPago")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvidenciaPagoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEvidenciaPago;
    private String image_Evidencia;

    @ManyToOne
    @JoinColumn(name = "idOrden")
    private OrdenModel idOrden;

}
