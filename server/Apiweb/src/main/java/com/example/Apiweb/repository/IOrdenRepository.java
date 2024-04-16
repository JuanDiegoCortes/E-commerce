package com.example.Apiweb.repository;

import com.example.Apiweb.model.OrdenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IOrdenRepository extends JpaRepository<OrdenModel, Integer> {

    @Query (value="SELECT *" +
            "FROM `E-commerce`.`Orden`" +
            "WHERE `cedula` =  :cedula", nativeQuery = true)
    List<OrdenModel> listarOrdenesPorCedula(@Param("cedula") Integer cedula);
}
