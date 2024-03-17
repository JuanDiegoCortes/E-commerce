package com.Ecommerce.domain;

import com.Ecommerce.model.ComentarioModel;
import com.Ecommerce.model.DisenoPModel;
import com.Ecommerce.model.UsuarioModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioDTO {
    private Integer idComentario;
    private String texto;
    private UsuarioModel cedula;
    private ComentarioModel subIdComentario;
    private DisenoPModel idDisenoP;

    @JsonProperty("DisenoP")
    List<Map<String, Integer>> DisenosP;
}
