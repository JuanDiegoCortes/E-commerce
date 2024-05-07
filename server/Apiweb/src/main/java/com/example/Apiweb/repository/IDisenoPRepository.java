package com.example.Apiweb.repository;

import com.example.Apiweb.model.DisenoPModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface IDisenoPRepository extends JpaRepository<DisenoPModel, Integer> {
    @Query(value="SELECT * " +
            "FROM DisenoP dp " +
            "WHERE dp.idOrdenProd = :idOrdenProd", nativeQuery = true)
    Optional<DisenoPModel> listarDisenoCompartidoPorIdOrden(@Param("idOrdenProd") int idOrdenProd);

    @Transactional
    @Modifying
    @Query(value="UPDATE `E-commerce`.`DisenoP` " +
            "SET `estado` = :estado " +
            "WHERE `idOrdenProd` = :idOrdenProd", nativeQuery = true)
    void actualizarEstadoDisenoPorIdOrdenProd(@Param("idOrdenProd") int idOrdenProd, @Param("estado") String estado);
}
