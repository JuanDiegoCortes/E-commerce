package com.example.Apiweb.repository;

import com.example.Apiweb.model.CategoriaModel;
import com.example.Apiweb.model.OrdenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICategoriaRepository extends JpaRepository<CategoriaModel, Integer> {
}
