package com.example.Apiweb.repository;

import com.example.Apiweb.model.CiudadModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICiudadRepository extends JpaRepository<CiudadModel, Integer> {
}
