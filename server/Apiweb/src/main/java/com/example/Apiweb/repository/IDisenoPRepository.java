package com.example.Apiweb.repository;

import com.example.Apiweb.model.DisenoPModel;
import com.example.Apiweb.model.enums.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IDisenoPRepository extends JpaRepository<DisenoPModel, Integer> {
    @Query(value="SELECT * " +
            "FROM DisenoP dp " +
            "WHERE dp.idOrdenProd = :idOrdenProd", nativeQuery = true)
    Optional<DisenoPModel> listarDisenoCompartidoPorIdOrden(@Param("idOrdenProd") int idOrdenProd);

    @Query(value="UPDATE `E-commerce`.`DisenoP` " +
            "SET estado = ':estado' " +
            "WHERE idOrdenProd = :idOrdenProd", nativeQuery = true)
    Optional<DisenoPModel> actualizarEstadoDisenoPorIdOrdenProd(@Param("idOrdenProd") int idOrdenProd, @Param("estado") Estado estado);
}
