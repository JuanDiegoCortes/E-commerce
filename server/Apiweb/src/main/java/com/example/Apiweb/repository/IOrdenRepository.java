package com.example.Apiweb.repository;

import com.example.Apiweb.model.OrdenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IOrdenRepository extends JpaRepository<OrdenModel, Integer> {

    @Query (value="SELECT o.idOrden, o.fecha, o.estado, o.metodoPago, o.precioTotal, op.idProducto, p.nombre AS nombreProducto, p.descripcion AS descripcionProducto, p.genero, p.precio AS precioProducto, p.estado AS estadoProducto, op.cantidad, op.ordenPersonalizacion \n" +
            "FROM Orden o JOIN OrdenProd op ON o.idOrden = op.idOrden JOIN Producto p ON op.idProducto = p.idProducto \n" +
            "WHERE o.cedula = :cedula", nativeQuery = true)
    List<Object> listarOrdenesPorCedula(@Param("cedula") Integer cedula);
}
