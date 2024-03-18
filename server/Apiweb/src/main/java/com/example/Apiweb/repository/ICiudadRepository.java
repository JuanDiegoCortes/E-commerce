package com.Ecommerce.repository;

import com.Ecommerce.model.CiudadModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICiudadRepository extends JpaRepository<CiudadModel, Integer> {
}
