package com.Ecommerce.repository;

import com.Ecommerce.model.ProductoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoRepository extends JpaRepository<ProductoModel, Integer> {
}
