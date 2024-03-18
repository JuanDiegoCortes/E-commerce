package com.Ecommerce.repository;

import com.Ecommerce.model.RolModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolRepository extends JpaRepository<RolModel, Integer> {
}
