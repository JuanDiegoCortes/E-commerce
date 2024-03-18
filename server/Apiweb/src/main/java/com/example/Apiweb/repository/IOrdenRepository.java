package com.Ecommerce.repository;

import com.Ecommerce.model.OrdenModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrdenRepository extends JpaRepository<OrdenModel, Integer> {
}
