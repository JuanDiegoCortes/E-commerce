package com.Ecommerce.service;

import com.Ecommerce.repository.ICategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CategoriaServiceImp implements ICategoriaService{
    @Autowired
    ICategoriaRepository categoriaRepository;
}
