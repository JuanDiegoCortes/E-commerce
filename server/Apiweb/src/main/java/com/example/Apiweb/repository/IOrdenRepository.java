package com.example.Apiweb.repository;

import com.example.Apiweb.model.OrdenModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrdenRepository extends JpaRepository<OrdenModel, Integer> {
}
