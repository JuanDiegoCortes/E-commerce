package com.example.Apiweb.repository;

import com.example.Apiweb.model.ProductoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoRepository extends JpaRepository<ProductoModel, Integer> {
}
