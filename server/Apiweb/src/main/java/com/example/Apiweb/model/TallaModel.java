package com.example.Apiweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Talla")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TallaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTalla;
    private String medida;
}
