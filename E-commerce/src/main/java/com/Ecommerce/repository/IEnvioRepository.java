package com.Ecommerce.repository;

import com.Ecommerce.model.EnvioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEnvioRepository extends JpaRepository<EnvioModel, Integer> {
}
