package com.example.Apiweb.repository;

import com.example.Apiweb.model.DepartamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDepartamentoRepository extends JpaRepository<DepartamentoModel, Integer> {
}
