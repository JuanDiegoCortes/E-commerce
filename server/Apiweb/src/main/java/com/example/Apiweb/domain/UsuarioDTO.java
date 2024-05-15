package com.example.Apiweb.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Integer cedula;
    private String nombre;
    private String correo;

    @JsonProperty("roles")
    List<Map<String, Object>> roles;
}
