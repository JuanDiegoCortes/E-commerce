package com.example.Apiweb.repository;

import com.example.Apiweb.model.DisenoPModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IDisenoPRepository extends JpaRepository<DisenoPModel, Integer> {
    @Query(value="SELECT dp.image_url, dp.idOrden, dp.cedula, dp.idDisenoP " +
            "FROM DisenoP dp " +
            "WHERE dp.idOrden = :idOrden", nativeQuery = true)
    List<Object> listarDisenosCompartidosPorIdOrden(@Param("idOrden") int idOrden);
}
