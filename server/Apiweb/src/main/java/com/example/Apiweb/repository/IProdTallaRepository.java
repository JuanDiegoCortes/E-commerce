package com.example.Apiweb.repository;

import com.example.Apiweb.model.ProdTallaModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProdTallaRepository extends JpaRepository<ProdTallaModel, Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE `e-commerce`.ProdTalla " +
            "SET cantidad = :cantidad " +
            "WHERE idProducto = :idProducto AND idTalla = :idTalla", nativeQuery = true)
    int actualizarCantidadProdTalla(@Param("cantidad") int cantidad, @Param("idProducto") int idProducto, @Param("idTalla") int idTalla);

    @Query(value = "SELECT * FROM ProdTalla WHERE idProducto = :idProducto AND idTalla = :idTalla", nativeQuery = true)
    ProdTallaModel obtenerProdTallaPorProductoYTalla(@Param("idProducto") int idProducto, @Param("idTalla") int idTalla);

    @Query(value = "SELECT * FROM ProdTalla WHERE idProducto = :idProducto", nativeQuery = true)
    List<ProdTallaModel> obtenerProdTallaPorIdProducto(@Param("idProducto") int idProducto);
}
