package com.example.Apiweb.repository;

import com.example.Apiweb.model.ComentarioModel;
import com.example.Apiweb.model.OrdenProdModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IComentarioRepository extends JpaRepository<ComentarioModel, Integer> {
    @Query(value = "SELECT * \n" +
            "FROM `E-commerce`.comentario as C \n" +
            "WHERE C.idDisenoP =  :idDisenoP", nativeQuery = true)
    List<ComentarioModel> listarComentariosPorIdDisenoP(@Param("idDisenoP") Integer idDisenoP);
}
