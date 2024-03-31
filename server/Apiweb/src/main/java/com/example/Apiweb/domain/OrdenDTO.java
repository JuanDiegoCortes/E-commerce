package com.example.Apiweb.domain;

import com.example.Apiweb.model.EnvioModel;
import com.example.Apiweb.model.UsuarioModel;
import com.example.Apiweb.model.enums.Estado;
import com.example.Apiweb.model.enums.MetodoPago;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenDTO {
    private Integer idOrden;
    private Date fecha;
    private Estado estado;
    private MetodoPago metodoPago;
    private Float precioTotal;
    private EnvioModel idEnvio;
    private UsuarioModel cedula;

    @JsonProperty("Productos")
    List<Map<String, Object>> Productos;
}
