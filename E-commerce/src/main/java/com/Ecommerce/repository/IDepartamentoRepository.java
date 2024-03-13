package com.Ecommerce.repository;

import com.Ecommerce.model.DepartamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDepartamentoRepository extends JpaRepository<DepartamentoModel, Integer> {
}
