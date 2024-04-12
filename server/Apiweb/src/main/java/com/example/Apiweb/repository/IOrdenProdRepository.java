package com.example.Apiweb.repository;

import com.example.Apiweb.model.OrdenModel;
import com.example.Apiweb.model.OrdenProdModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IOrdenProdRepository extends JpaRepository<OrdenProdModel, Integer> {
    @Query(value = "SELECT * " +
            "FROM `E-commerce`.`OrdenProd` " +
            "WHERE `idOrden` =  :idOrden", nativeQuery = true)
    List<OrdenProdModel> listarProductosPorIdOrden(@Param("idOrden") Integer idOrden);
}
