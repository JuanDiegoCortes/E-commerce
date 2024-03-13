package com.Ecommerce.service;

import com.Ecommerce.repository.IDepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class DepartamentoServiceImp implements IDepartamentoService{
    @Autowired
    IDepartamentoRepository departamentoRepository;
}
