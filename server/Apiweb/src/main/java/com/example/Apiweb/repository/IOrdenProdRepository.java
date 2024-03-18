package com.Ecommerce.repository;

import com.example.Apiweb.model.OrdenProdModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrdenProdRepository extends JpaRepository<OrdenProdModel, Integer> {
}
