package com.Ecommerce.domain;

import com.Ecommerce.model.EnvioModel;
import com.Ecommerce.model.UsuarioModel;
import com.Ecommerce.model.enums.Estado;
import com.Ecommerce.model.enums.MetodoPago;
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
    List<Map<String, Integer>> Productos;
}
