package com.Ecommerce.repository;

import com.Ecommerce.model.OrdenProdModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrdenProdRepository extends JpaRepository<OrdenProdModel, Integer> {
}
